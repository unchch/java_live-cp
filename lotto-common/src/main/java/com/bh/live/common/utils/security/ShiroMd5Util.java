package com.bh.live.common.utils.security;

import com.bh.live.common.utils.string.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * shiro工具
 *
 * @author yq.
 * @version 1.0.0 2019-04-07
 * @since 1.0.0 2019-04-07
 **/
public class ShiroMd5Util {

    /**
     * 对原密码进行md5加盐多次加密
     *
     * @param source 原密码
     * @param salt   盐值
     * @param times  加密次数
     * @return 加密后密码
     */
    public static String genMd5Pass(String source, String salt, int times) {
        SimpleHash sh = new SimpleHash("MD5", source, salt, times);
        return sh.toString();
    }

    /**
     * 对原密码进行md5加盐加密 加密1024次
     *
     * @param source 原密码
     * @param salt   盐值
     * @return 加密后密码
     */
    public static String genMd5Pass(String source, String salt) {
        SimpleHash sh = new SimpleHash("MD5", source, salt, 1024);
        return sh.toString();
    }

    public static void main(String[] args) {
        String salt = StringUtils.getRandomString(6);
        System.out.println(salt);
        System.out.println(genMd5Pass("123456", salt));
    }

}
