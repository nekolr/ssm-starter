package com.nekolr.util;

import com.nekolr.annotation.PropertiesBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Spring 配置转换工具类
 *
 * @author nekolr
 */
public class EnvironmentUtils {

    private EnvironmentUtils() {

    }

    /**
     * 将配置转换成 bean
     *
     * @param env  Spring 环境
     * @param type bean 类型
     * @return
     * @throws Exception
     */
    public static <T> T toBean(Environment env, Class<T> type) throws Exception {
        T bean = type.newInstance();
        Annotation propertiesBean = type.getAnnotation(PropertiesBean.class);
        if (propertiesBean != null) {
            String prefix = ((PropertiesBean) propertiesBean).prefix();
            Field[] fields = type.getDeclaredFields();
            for (Field field : fields) {
                String envFieldName;
                if (StringUtils.isBlank(prefix)) {
                    envFieldName = field.getName();
                } else {
                    envFieldName = prefix + "." + field.getName();
                }
                Object value = env.getProperty(envFieldName);
                if (value != null) {
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                    Method write = propertyDescriptor.getWriteMethod();
                    write.setAccessible(true);
                    write.invoke(bean, cast(write.getParameterTypes()[0], value));
                }
            }
        }
        return bean;
    }


    /**
     * 类型转换
     *
     * @param type  类型全限定名
     * @param value String 值
     * @return
     */
    private static Object cast(Class<?> type, Object value) {
        String v = (String) value;
        String typeName = type.getTypeName();
        switch (typeName) {
            case "java.lang.Integer":
                value = Integer.valueOf(v);
                break;
            case "java.lang.Boolean":
                value = Boolean.valueOf(v);
                break;
            case "java.lang.Double":
                value = Double.valueOf(v);
                break;
            case "java.lang.Float":
                value = Float.valueOf(v);
                break;
            case "java.lang.Long":
                value = Long.valueOf(v);
                break;
            default:
                value = v;
                break;
        }
        return value;
    }
}
