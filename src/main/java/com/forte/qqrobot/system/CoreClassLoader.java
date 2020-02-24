package com.forte.qqrobot.system;

/**
 * ClassLoader
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public class CoreClassLoader extends ClassLoader {
    private static final CoreClassLoader CORE_CLASS_LOADER = new CoreClassLoader();
    private CoreClassLoader(){ }
    public static CoreClassLoader getCoreClassLoader(){
        return CORE_CLASS_LOADER;
    }

}
