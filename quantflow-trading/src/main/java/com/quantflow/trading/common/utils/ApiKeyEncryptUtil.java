package com.quantflow.trading.common.utils;


import com.quantflow.common.utils.sign.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * API Key 加密工具类
 * 简单的AES加密，生产环境建议使用更安全的方案
 */
public class ApiKeyEncryptUtil {

    // 加密密钥（16位），生产环境应该从配置文件读取
    private static final String SECRET_KEY = "QuantFlow2025Key";
    // 建议使用 AES-256
    // 密钥应该从环境变量或配置中心获取
//    private static final String SECRET_KEY = System.getenv("QUANTFLOW_SECRET_KEY");
    private static final String ALGORITHM = "AES";

    /**
     * 加密
     */
    public static String encrypt(String data) {
        if (data == null || data.isEmpty()) {
            return data;
        }
        try {
            SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encrypted = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.encode(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("加密失败", e);
        }
    }

    /**
     * 解密
     */
    public static String decrypt(String encryptedData) {
        if (encryptedData == null || encryptedData.isEmpty()) {
            return encryptedData;
        }
        try {
            SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decrypted = cipher.doFinal(Base64.decode(encryptedData));
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("解密失败", e);
        }
    }
}