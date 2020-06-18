package com.forte.qqrobot.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 记录run parameter列表
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public class RunParameters {

    private List<RunParameter> parameters;
    private Map<String, RunParameter> parameterMap;

    public RunParameters(RunParameter... parameters){
        this.parameters = new ArrayList<>();
        this.parameterMap = new HashMap<>();
        for (RunParameter parameter : parameters) {
            this.parameters.add(parameter);
            parameterMap.put(parameter.getName(), parameter);
        }
    }

    public List<RunParameter> getParameters(){
        return new ArrayList<>(parameters);
    }

    public Map<String, RunParameter> getParameterMap() {
        return new HashMap<>(parameterMap);
    }

    /**
     * 获取的时候不需要使用前缀, 例如：'--'
     * @param name
     * @return
     */
    public RunParameter getParameter(String name){
        return parameterMap.get(name);
    }
}
