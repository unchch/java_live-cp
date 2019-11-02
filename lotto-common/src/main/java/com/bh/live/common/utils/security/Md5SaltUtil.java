package com.bh.live.common.utils.security;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.util.Random;

/**
 * MD5加盐算法   每个用户随机不同盐值，生成48位密码
 */
public class Md5SaltUtil {

    /**
     * 生成含有随机盐的密码
     *
     * @param password
     * @return
     */
    public static String generate(String password) {
        String salt = getSalt(16);

        password = md5Hex(password + salt);
        char[] cs = new char[48];
        for (int i = 0; i < 48; i += 3) {
            cs[i] = password.charAt(i / 3 * 2);
            char c = salt.charAt(i / 3);
            cs[i + 1] = c;
            cs[i + 2] = password.charAt(i / 3 * 2 + 1);
        }
        return new String(cs);
    }

    /**
     * 生成随机盐（一般大于等于16位）
     *
     * @param len
     * @return
     */
    public static String getSalt(int len) {
        Random r = new Random();
        StringBuilder sb = new StringBuilder(len);
        sb.append(r.nextInt(99999999)).append(r.nextInt(99999999));
        int length = sb.length();
        if (length < len) {
            for (int i = 0; i < len - length; i++) {
                sb.append("0");
            }
        }
        String salt = sb.toString();
        return salt;
    }

    /**
     * 校验密码是否正确
     *
     * @param password
     * @param md5
     * @return
     */
    public static boolean verify(String password, String md5) {
        try {
            char[] cs1 = new char[32];
            char[] cs2 = new char[16];
            for (int i = 0; i < 48; i += 3) {
                cs1[i / 3 * 2] = md5.charAt(i);
                cs1[i / 3 * 2 + 1] = md5.charAt(i + 2);
                cs2[i / 3] = md5.charAt(i + 1);
            }
            String salt = new String(cs2);
            return md5Hex(password + salt).equals(new String(cs1));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取十六进制字符串形式的MD5
     *
     * @param src
     * @return
     */
    public static String md5Hex(String src) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bs = md5.digest(src.getBytes());
            return new String(new Hex().encode(bs));
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(generate("123456"));
        System.out.println(generate("123456"));
        System.out.println(verify("123456","082a09318b26e89724b2e377083295c02c9ca06e1415988e"));
        System.out.println(verify("123456","e90793d47f9e34431618368812483143839dc5ae4bb8823c"));
    }
}
