package com.nekolr.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 字符串工具
 *
 * @author nekolr
 */
public class StringHelper {

    private StringHelper() {

    }

    /**
     * 将字符串拆分放入 Set
     *
     * @param value 字符串
     * @param regex 分隔符
     * @return
     */
    public static Set<String> split2Set(String value, String regex) {
        Set<String> set = new HashSet<>();
        if (StringUtils.isEmpty(value)) {
            return set;
        }
        set.addAll(Arrays.asList(StringUtils.split(value, regex)));
        return set;
    }

    /**
     * 将字符串拆分
     *
     * @param value 字符串
     * @param regex 分隔符
     * @return
     */
    public static String[] split(String value, String regex) {
        if (StringUtils.isEmpty(value)) {
            return new String[0];
        }
        return StringUtils.split(value, regex);
    }

}
