package com.forte.qqrobot.system;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.forte.qqrobot.ResourceDispatchCenter;
import com.forte.qqrobot.anno.Version;
import com.forte.qqrobot.log.QQLogLang;
import com.forte.qqrobot.sender.HttpClientAble;
import com.forte.qqrobot.sender.HttpClientHelper;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * 核心中类似于System的类, 也类似于一种工具类或者静态资源类。会逐步取代{@link ResourceDispatchCenter}中的部分功能。
 * @author ForteScarlet
 * @version  1.7.0
 */
@Version(version = "1.13.0",
        versionFamily = "1.13")
public final class CoreSystem {

    /** 当前程序的RUN_TIME对象 */
    private static final Runtime RUN_TIME;
    /** 当前可用的cpu核心数量 */
    private static final int CPU_CORE;
    /** 当前核心的版本号, 如果未知即为“UNKNOWN” */
    private static final String CORE_VERSION;
    /** 日志 */
    private static final QQLogLang LOG_LANG = new QQLogLang("system");
    /** 核心版本的key名称 */
    private static final String SIMPLE_ROBOT_CORE_VERSION_KEY ;

    // 加载资源
    static {
        // 首先初始化Runtime参数
        RUN_TIME = Runtime.getRuntime();
        CPU_CORE = RUN_TIME.availableProcessors();
        // 初始化版本号
        Version versionAnnotation = CoreSystem.class.getAnnotation(Version.class);
        if(versionAnnotation != null){
            CORE_VERSION = versionAnnotation.version();
        }else{
            CORE_VERSION = "UNKNOWN";
        }
        SIMPLE_ROBOT_CORE_VERSION_KEY = "simplerobot.core.version";
        // 核心版本
        System.setProperty(SIMPLE_ROBOT_CORE_VERSION_KEY, CORE_VERSION);

    }

    /**
     * 检测版本是否与最新版一致
     */
    public static void checkVersion(){
        // 初始化版本号
        Version versionAnnotation = CoreSystem.class.getAnnotation(Version.class);
        // 1.x版本下的groupId与artifactId
        String coreGroupId = versionAnnotation.groupId();
        String coreArtifactId = versionAnnotation.artifactId();
        String versionFamily = versionAnnotation.versionFamily();
        String versionFamilySearch = versionFamily + ".*";
        // 如果未知，则不提示也不检测
        if(!"UNKNOWN".equals(CORE_VERSION)){
            try{
                LOG_LANG.warning("version.check");

                // 检测版本。版本检测url为阿里云的maven镜像地址
                // 可以抽空换成oss路径 https://oss.sonatype.org/content/groups/public/io/github/ForteScarlet/simple-robot-core/
                String url = "https://maven.aliyun.com/artifact/aliyunMaven/searchArtifactByGav?_input_charset=UTF-8&groupId="+ coreGroupId +"&artifactId="+ coreArtifactId +"&version="+ versionFamilySearch +"&repoId=all";

                String get = HttpClientHelper.getDefaultHttp().get(url);

                JSONArray jsonArray = JSONObject.parseObject(get).getJSONArray("object");

                final String firstVersion = jsonArray.stream().map(o -> o instanceof JSONObject ? (JSONObject) o : null)
                        .filter(Objects::nonNull)
                        .filter(o -> "sources".equals(o.getString("classifier")))
                        .findFirst().map(f -> f.getString("version")).orElse(null);

                boolean newer = false;
                if(firstVersion != null && !CORE_VERSION.equals(firstVersion)){
                    try {
                        final String[] coreSplit = CORE_VERSION.split("\\.");
                        final int coreVersionNumber = Integer.parseInt(coreSplit[coreSplit.length - 1]);
                        final String[] firstSplit = firstVersion.split("\\.");
                        final int firstVersionNumber = Integer.parseInt(firstSplit[firstSplit.length - 1]);
                        newer = firstVersionNumber > coreVersionNumber;
                    }catch (Exception ignored){}
                }

                String newerVersion = newer ? firstVersion : null;

                if(newer){
                    // 有最新的，即当前不是最新的
                    String url1 = "https://mvnrepository.com/artifact/"+coreGroupId+"/" + coreArtifactId + "/" + newerVersion;
                    String url2 = "https://search.maven.org/artifact/"+ coreGroupId +"/" + coreArtifactId + "/"+ newerVersion +"/jar";
                    LOG_LANG.warning("version.notnewest", CORE_VERSION, versionFamily, newerVersion, url1, url2);
                }else{
                    // 没有最新的，即当前是最新的
                    LOG_LANG.success("version.newest", CORE_VERSION, versionFamily);
                }
            }catch (Exception e){
                LOG_LANG.warning("version.check.failed");
                LOG_LANG.debug("version.check.failed", e);
            }
        }
    }

    /**
     * 检测版本
     * @param version  当前版本
     * @param groupId   坐标的groupId
     * @param artifactId 坐标的artifactId
     * @return key为版本系，value为是否存在更新版本
     */
    private static Map.Entry<String, Boolean> hasNewerVersion(String version, String groupId, String artifactId){
        HttpClientAble defaultHttp = HttpClientHelper.getDefaultHttp();

        String[] vs = version.split("\\.");
        int lastVersionUpper = Integer.parseInt(vs[vs.length - 1]) + 1;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < vs.length-2; i++) {
            sb.append(vs[i]).append('.');
        }
        sb.append(vs[vs.length - 2]);
        // 版本头， 例如1.8
        String versionHeader = sb.toString();
        // 版本系，例如1.8.x
        String versionFamily = sb.append(".x").toString();

        AbstractMap.SimpleEntry<String, Boolean> end = new AbstractMap.SimpleEntry<>(versionFamily, null);
        // 高一个版本

        String upperVs = versionHeader + "." + lastVersionUpper++;
        String upperVsPlus = versionHeader + "." + lastVersionUpper;

        String url = "https://mvnrepository.com/artifact/"+ groupId +"/"+ artifactId +"/";

        // 检测是否有高一级的版本
        String checkUrl1 = url + upperVs;
        LOG_LANG.debug("version.check.url", checkUrl1);
        String checkString1 = defaultHttp.get(checkUrl1);
        if(checkString1 != null){
            end.setValue(true);
            return end;
        }
        // 尝试在加1级别的小等级
        String checkUrl2 = url + upperVsPlus;
        LOG_LANG.debug("version.check.url", checkUrl2);
        String checkString2 = defaultHttp.get(checkUrl2);
        if(checkString2 != null){
            end.setValue(true);
            return end;
        }

        // 认为没有最新的
        end.setValue(false);
        return end;
    }


    public static String getCoreVersion(){
        return CORE_VERSION;
    }


    /**
     * 获取程序可用的CPU核心数量
     * @return cpu核心数量
     */
    public static int getCpuCore(){
        return CPU_CORE;
    }

    /**
     * <pre> 根据核心数量和阻塞系数计算一个线程池所需要的最佳线程数量。
     * <pre> 最佳的线程数 = CPU可用核心数 / (1 - 阻塞系数)
     * <pre> 阻塞系数代表整个任务下来，线程有百分之多少的时间是处于阻塞状态的。
     * @param blockingFactor 阻塞系数，0~1之间, 不可等于1
     * @return 线程数量
     */
    public static int getBestPoolSize(double blockingFactor){
        if(blockingFactor < 0 || blockingFactor >= 1){
            throw new IllegalArgumentException("block factor must in [0, 1), and cannot be 1, but: " + blockingFactor);
        }
        // 最佳的线程数 = CPU可用核心数 / (1 - 阻塞系数)
        return (int) (CPU_CORE / (1 - blockingFactor));
    }



    /**
     * @see System#getProperties()
     * @return System properties.
     */
    public static Properties getProperties(){
        return System.getProperties();
    }



}
