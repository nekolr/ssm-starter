package com.nekolr.util;

/**
 * 类工具
 *
 * @author nekolr
 */
public class ClassUtils {

    /**
     * 获取类加载器
     *
     * @return
     */
    public static ClassLoader getDefaultClassLoader() {
        ClassLoader classLoader = null;
        try {
            // 先取当前线程的类加载器
            classLoader = Thread.currentThread().getContextClassLoader();
        } catch (Throwable var1) {
        }
        if (classLoader == null) {
            // 当前类的类加载器
            classLoader = ClassUtils.class.getClassLoader();
            if (classLoader == null) {
                try {
                    // 当前应用的类加载器
                    classLoader = ClassLoader.getSystemClassLoader();
                } catch (Throwable var2) {
                }
            }
        }
        return classLoader;
    }
}
