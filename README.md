# 基于接口的通讯机器人java开发框架-核心
[![](https://img.shields.io/badge/simple--robot-core-green)](https://github.com/ForteScarlet/simple-robot-core)  [![img](https://camo.githubusercontent.com/f8464f5d605886b8369ab6daf28d7130a72fd80e/68747470733a2f2f696d672e736869656c64732e696f2f6d6176656e2d63656e7472616c2f762f696f2e6769746875622e466f727465536361726c65742f73696d706c652d726f626f742d636f7265)](https://search.maven.org/artifact/io.github.ForteScarlet/simple-robot-core) [![](https://img.shields.io/badge/%E7%9C%8B%E4%BA%91%E6%96%87%E6%A1%A3-doc-green)](https://www.kancloud.cn/forte-scarlet/simple-coolq-doc)  [![](https://img.shields.io/badge/QQ%E7%BE%A4-782930037-blue)](https://jq.qq.com/?_wv=1027&k=57ynqB1)  


> 项目对应GITHUB地址: https://github.com/ForteScarlet/simple-robot-core
>
> 项目对应GITEE地址:  https://gitee.com/ForteScarlet/simple-robot-core

**首先，点击项目右上角的`star`以开启隐藏链接。**

核心版本：[![img](https://camo.githubusercontent.com/f8464f5d605886b8369ab6daf28d7130a72fd80e/68747470733a2f2f696d672e736869656c64732e696f2f6d6176656e2d63656e7472616c2f762f696f2e6769746875622e466f727465536361726c65742f73696d706c652d726f626f742d636f7265)](https://search.maven.org/artifact/io.github.ForteScarlet/simple-robot-core)


<br>

**文档请见文档地址：**
http://simple-robot-doc.forte.love
或
https://www.kancloud.cn/forte-scarlet/simple-coolq-doc
<br>

**在线javadoc文档(由码云平台生成)：**
 https://apidoc.gitee.com/ForteScarlet/simple-robot-core

由于目前核心部分与组件部分相互分离，所以核心与组件的github地址也相互分离了。
所有组件均依赖于此**核心框架**，支持maven坐标，具体坐标请见**文档下文**或**入群咨询**

点击`star`后即可申请加入QQ群聊，QQ群号：**782930037**

<br>
<br>

## 这是什么？
这是一个基于网络通讯接口的通讯机器人开发框架，可以对接例如酷Q机器人等一系列通讯机器人应用。
此框架分有两个部分：核心框架与组件框架，当前仓库为核心框架。

### 核心框架？
 核心框架提供主要的功能与接口，用于为组件框架提供实现接口，并借此可以实现不同平台、不同插件的简易切换。
 核心框架提供标准化接口、功能，但是不能够直接使用。
 
### 组件框架？
 组件框架依赖于核心框架，其存在的主要目的就是为了针对某一个特定的插件（例如酷Q的CQ HTTP API插件）进行对接。

### 为何要分离？
 举个例子。假如你想要使用此框架对接 **A应用** ，那么你就要使用 **组件框架A** 来开发，然后过了两个月，你发现**B应用**也挺好的，想要切换到**B应用**，这时候分离的作用就出现了，你只需要将依赖的**组件框架A**切换为**组件框架B**，然后简单修改一下启动类和配置信息，而不需要修改任何功能性的代码（例如消息监听器、定时任务等等）。因为绝大部分功能性的东西都是核心框架所提供的，而你切换组件是不会变更核心的，这样就可以做到能够很好的去支持更多的应用，且切换起来也不会太繁琐。

> 简单来讲，核心框架就是一块儿主板，组件框架就是一块儿显卡，而最终开机后，电脑里的各种各样的应用程序，就是你最终所书写的功能性代码。
<br>
只要这块儿主板不变，每次更换显卡只需要重新安装一下显卡驱动，而不需要删除所有的应用。

也得益于核心组件分离，使其能够有更大的拓展空间，而不是仅局限于酷Q应用，也不一定局限于腾讯QQ的业务范围。毕竟有着“私信、群聊”等等一系列信息内容的应用可不仅限于腾讯QQ

<br>

### 有何优点？

- 有着极高的扩展性。任何人都可以根据核心框架，并针对某一个应用或者接口来开发组件框架，并投入使用。
- 核心提供了极其丰富的功能，例如
    - 注解开发风格
    - 丰富的过滤规则
    - 集成quartz定时任务框架
    - 依赖注入
    - 部分拦截器
    - 支持与Spring(boot(+MyBatis))等其他框架的整合
    - 目前针对Springboot，提供了快速启动器(starter)
- 持续更新的代码
- 好说话的作者（欢迎入群与群主交流催更😏
- 高度接口化，可扩展性强
- 有虽然没有完全写完但是已经很全面了的中文文档
- 已上传Maven中央仓库，支持Maven、gradle等项目架构方式        
  


## 现在已经存在的应用？

#### 已经存在的**组件框架**：

| 平台 |      依赖       |                           项目地址                           |
| :--: | :-------------: | :----------------------------------------------------------: |
| 酷Q（停止）  |    LEMOC插件    | https://github.com/ForteScarlet/simple-robot-component-lemoc |
| 酷Q（停止）  | HTTP TO CQ插件  | https://github.com/ForteScarlet/simple-robot-component-httpapi |
| 酷Q（停止）  | CQ HTTP API插件 | https://github.com/ForteScarlet/simple-robot-component-coolHttpApi |
| 酷Q（停止）  | JCQ插件 | https://github.com/ForteScarlet/simple-robot-component-JCQ |
| JVM          | Mirai（停止维护）   | https://github.com/ForteScarlet/simple-robot-component-mirai | 

> `酷Q已停止服务。R.I.P.`

> `Mirai已停止维护。`

#### 已经存在的**模组**：

| 名称 |      描述       |                           项目地址                           |
| :--: | :-------------: | :----------------------------------------------------------: |
| cqcodeutils模组  |    提供高效的cq码操作工具。可独立依赖、使用。    | https://github.com/ForteScarlet/simple-robot-module-cqcodeutils |
| delay-task模组  |    提供基于携程的异步延时任务功能。可独立依赖、使用。    | https://github.com/ForteScarlet/simple-robot-module-delay-task |
| redis-bot-manager模组  |    提供使用redis统一管理bot账号的功能。    | 暂无仓库，文档：http://simple-robot-doc.forte.love/1672448 |
| debugger模组  |    提供一系列便于本地、远程debug的功能的模组。    | https://github.com/ForteScarlet/simple-robot-module-debuger-common |


#### 已经存在的**Springboot-starter**

| 名称 |      描述       |                           项目地址                           | 
| :--: | :-------------: | :----------------------------------------------------------: | 
|   core-starter  |    starter项目的父项目，提供一些基础配置    | https://github.com/ForteScarlet/simple-robot-core-springboot-starter |
|   cqhttp-starter  |    cqhttp组件的starter  | https://github.com/ForteScarlet/simple-robot-component-cqhttp-springboot-starter |
|   mirai-starter  |    mirai组件的starter    | https://github.com/ForteScarlet/simple-robot-component-mirai-springboot-starter |


### 公开Demo

如果你想要尝试一下，不妨在阅读过文档的快速开始的情况下，去[公开Demo项目(gitee)](https://gitee.com/ForteScarlet/simple-robot-demo-project)看一看。



> 如果你根据核心开发了组件框架，可以告知我，我会更新在此处与下文处。

> 如果你只是想开发一个QQ机器人，而不在乎使用什么平台，请参考文档开头提到的组件框架。

# 安装

此处仅对如何搭建Java项目进行说明，如果你想开发QQ机器人， 请移步至 [文档](https://www.kancloud.cn/forte-scarlet/simple-coolq-doc) 查看。

首先你需要知道，此项目(即`核心`) **无法直接使用**，如果你想要开发酷Q平台的QQ机器人，请移步至 [文档](https://www.kancloud.cn/forte-scarlet/simple-coolq-doc) 查看。

**其次，以下列举的部署方式中，版本号请自行修改为最新版。**

你可以选择使用以下方法进行自动部署：

版本参考：[![img](https://camo.githubusercontent.com/f8464f5d605886b8369ab6daf28d7130a72fd80e/68747470733a2f2f696d672e736869656c64732e696f2f6d6176656e2d63656e7472616c2f762f696f2e6769746875622e466f727465536361726c65742f73696d706c652d726f626f742d636f7265)](https://search.maven.org/artifact/io.github.ForteScarlet/simple-robot-core)

## Maven 

```xml
<dependency>
    <groupId>io.github.ForteScarlet</groupId>
    <artifactId>simple-robot-core</artifactId>
    <version>${version}</version>
</dependency>
```

## Gradle

```
// https://mvnrepository.com/artifact/io.github.ForteScarlet/simple-robot-core
compile group: 'io.github.ForteScarlet', name: 'simple-robot-core', version: '>${version}'
```



如果你不选择使用自动部署的方式，你可以直接前往 [Maven下载Jar](https://mvnrepository.com/artifact/io.github.ForteScarlet/simple-robot-core) 包或者碰碰运气，看看[releases](https://github.com/ForteScarlet/simple-robot-core/releases)有没有最新的Jar包。

注意，当你选择下载jar包的时候，请同时下载项目中`lib`文件夹下的依赖包。




## 看的有点蒙，但是想试试
可以考虑：
- 加入QQ群：782930037, 如果对水群没有兴趣，可以直接去找群主。
- 邮箱：ForteScarlet@163.com (邮箱信息查看周期较长)
- GITEE或者GITHUB留言

首先建议进群交流，毕竟其他两个我并不经常看(●ˇ∀ˇ●)


## 建议、意见、bug反馈
- 你可以使用`issue`向我反馈bug或者提出建议意见。
- 你可以加入QQ群聊`782930037`向我反馈bug或者提出建议意见。
- 你可以通过联系邮箱`ForteScarlet@163.com`向我反馈bug或者提出建议意见（可能会处理不及时）。

## 更新计划
对于未来的更新计划可以从github的projects中或者[更新计划](更新计划.md)中看到。一般想到什么的话会优先更新`更新计划.md`。


## 赞助一下？

那真的真的太感谢了，你可以参考 [文档/捐助]( https://www.kancloud.cn/forte-scarlet/simple-coolq-doc/1115825 ) 或者去 [爱发电](https://afdian.net/@ForteScarlet) 逛逛~



## 大家的成果
<table>
    <thead>
        <tr>
            <th>项目名称</th>
            <th>作者</th>
            <td>项目简介</td>
            <td>项目链接</td>
		</tr>
    </thead>
    <tbody> 
        <tr>
            <td>
                <p>崩坏学园2小助手 - 萌萌新</p> 
            </td>
            <th>瑶光天枢</th>
            <td style="word-wrap:break-word;word-break:break-all;" width="120px">崩坏学园2的在线群聊，查询装备，模拟扭蛋，查询up记录，来份色图的机器人</td>
            <td>https://github.com/LiChen233/simple-robot</td>
        </tr>
        <tr>
            <td>
                <p>群管机器人</p> 
            </td>
            <th rowspan='3'>会跑的仓鼠</th>
            <td style="word-wrap:break-word;word-break:break-all;" width="120px">基于simple开发的群机器人</td>
            <td>https://gitee.com/yaozhenyong/cqrobotjar2</td>
        </tr>
        <tr>
            <td>
                <p>游戏查询机器人</p> 
            </td>
            <td style="word-wrap:break-word;word-break:break-all;" width="120px">基于simple开发的娱乐性机器人</td>
            <td>https://gitee.com/yaozhenyong/cqrobotIndependent</td>
        </tr>
        <tr>
            <td>
                <p>云端控制面板</p>
            </td>
            <td style="word-wrap:break-word;word-break:break-all;" width="120px">主要做上面两个版本机器人的控制面板（web端）	</td>
            <td>https://gitee.com/yaozhenyong/Qqrobotwar</td>
        </tr>
        <tr>
            <td>
                <p>Robot-Spring</p>
            </td>
            <th>千年老妖(1571650839)</th>
            <td style="word-wrap:break-word;word-break:break-all;" width="120px">
                <p>基于酷Q的，使用Java语言开发的，面向COC的骰子机器人组件, 
                使用架构为SpringBoot-Mybatis-CoolHttpApI。主要功能：COC跑团基础功能+斗图,聊天,群管等乱七八糟的功能</p>
            </td>
            <td>https://github.com/17336324331/Robot-Spring</td>
        </tr>
        <tr>
            <td>
                <p>coolqhttpapisimpleyiluoeandxishirobot</p>
            </td>
            <th>以罗伊</th>
            <td style="word-wrap:break-word;word-break:break-all;" width="120px">
                就是用simple-robot框架写的一个小机器人啦,windows linux上都能正确运行(目前)
            </td>
            <td>https://github.com/YiluoE/coolqhttpapisimpleyiluoeandxishirobot</td>
        </tr>
</tbody>
</table>  



