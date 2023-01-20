package com.pdx.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.Key;

/**
 * @Author: 派大星
 * @Date: 2023/01/21 2023/1/21
 * @Description:
 */
@Slf4j
public class DesUtils {
    private static Key key;
    private static String KEY_STR = "12345678";//注意这里长度要8位

    static {
        try {
            DESKeySpec keySpec = new DESKeySpec(KEY_STR.getBytes("utf-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            key = keyFactory.generateSecret(keySpec);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


    /* 2、对字符串进行DES加密，返回BASE64编码的加密字符串 */
    public static String encrypt(String src/* 明文 */) {
        try {
            byte[] src_byte = src.getBytes("UTF-8");
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] final_byte = cipher.doFinal(src_byte);
            return Base64.encodeBase64URLSafeString(final_byte);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /* 3、对BASE64编码的加密字符串进行解密，返回解密后的字符串 */
    public static String decrypt(String src) {
        try {
            byte[] src_byte = Base64.decodeBase64(src);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decrypt_byte = cipher.doFinal(src_byte);
            return new String(decrypt_byte, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }


    public static void main(String[] args) {
        String encrypt = DesUtils.encrypt("admin123");
        System.out.println(encrypt);
        String decrypt = DesUtils.decrypt(encrypt);
        System.out.println(decrypt);
    }
}
