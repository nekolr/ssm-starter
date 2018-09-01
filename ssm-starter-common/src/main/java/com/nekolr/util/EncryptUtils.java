package com.nekolr.util;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;

/**
 * 加密、解密套件
 *
 * @author nekolr
 */
public class EncryptUtils {

    /**
     * AES 加密
     * @param original
     * @return
     */
    public static String aesEncrypt(String original) {
        // 构建
        AES aes = SecureUtil.aes("A!bq6^Jrs'94Kp/V".getBytes());
        // 加密
        return aes.encryptBase64(original);
    }

    /**
     * AES 解密
     * @param encrypt
     * @return
     */
    public static String aesDecrypt(String encrypt) {
        // 构建
        AES aes = SecureUtil.aes("A!bq6^Jrs'94Kp/V".getBytes());
        // 解密
        return aes.decryptStrFromBase64(encrypt);
    }

}
