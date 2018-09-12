package com.nekolr.util;

import java.util.UUID;

/**
 * ID 生成器
 *
 * @author nekolr
 */
public class IdGenerator {

    private IdGenerator() {

    }

    /**
     * 获取随机 UUID
     *
     * @return
     */
    public static String randomUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
