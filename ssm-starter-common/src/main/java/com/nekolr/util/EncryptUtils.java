package com.nekolr.util;

import cn.hutool.crypto.CryptoException;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;

/**
 * 加密、解密套件
 *
 * @author nekolr
 */
public class EncryptUtils {

    private EncryptUtils() {

    }

    /**
     * AES 加密，只用于配置文件加密
     *
     * @param original 原文
     * @return
     */
    public static String aesEncrypt(String original) {
        // 构建
        AES aes = SecureUtil.aes("A!bq6^Jrs'94Kp/V".getBytes());
        // 加密
        return aes.encryptBase64(original);
    }

    /**
     * AES 解密，只用于配置文件解密
     *
     * @param encrypt 密文
     * @return
     */
    public static String aesDecrypt(String encrypt) {
        // 构建
        AES aes = SecureUtil.aes("A!bq6^Jrs'94Kp/V".getBytes());
        // 解密
        return aes.decryptStrFromBase64(encrypt);
    }

    /**
     * AES 加密
     *
     * @param original 原文
     * @param secret   密钥
     * @return
     */
    public static String aesEncrypt(String original, String secret) {
        // 构建
        AES aes = SecureUtil.aes(secret.getBytes());
        // 加密
        return aes.encryptBase64(original);
    }

    /**
     * AES 解密
     *
     * @param encrypt 密文
     * @param secret  密钥
     * @return
     * @throws CryptoException 当密文格式不符合或者密文不匹配时抛出该异常
     */
    public static String aesDecrypt(String encrypt, String secret) throws CryptoException {
        // 构建
        AES aes = SecureUtil.aes(secret.getBytes());
        // 解密
        return aes.decryptStrFromBase64(encrypt);
    }

    /**
     * MD5 加密
     *
     * @param original
     * @return
     */
    public static String md5(String original) {
        return SecureUtil.md5(original);
    }

    public static void main(String[] args) {
        System.out.println(EncryptUtils.aesDecrypt("exu4nM0eUWCl0n3os5398A=="));
    }

}
