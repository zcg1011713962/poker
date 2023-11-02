package org.cloud.util;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class Md5Util {

    public static String calculateMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5"); // 创建 MD5 加密算法实例
            byte[] inputBytes = input.getBytes(); // 将字符串转换为字节数组
            byte[] hashBytes = md.digest(inputBytes); // 计算 MD5 哈希值

            // 将字节数组转换为十六进制字符串
            StringBuilder builder = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) builder.append('0');
                builder.append(hex);
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error("{}", e);
            return null;
        }
    }
}
