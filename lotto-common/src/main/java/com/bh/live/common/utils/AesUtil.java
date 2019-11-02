package com.bh.live.common.utils;

import com.bh.live.common.utils.string.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class AesUtil {

    public static void main(String args[]) throws Exception {

        String content = "123456a";

        String tokenKey = StringUtils.getRandomString(16);
        String userKey = StringUtils.getRandomString(16);
        System.out.println("tokenKey:"+tokenKey+",userKey:"+userKey);

        tokenKey = "sh23dgtmkjkmm6tz";
        userKey = "9hs8ax836g2x7a75";

        //加密
        String encrypted = encrypt(content, tokenKey, userKey);
        encrypted = "8SPurL+ZsDvkooleZz2ZCw==";
        //解密
        String decrypted = decrypt(encrypted, tokenKey, userKey);

        System.out.println("加密前：" + content);

        System.out.println("加密后：" + encrypted);

        System.out.println("解密后：" + decrypted);
    }

    /**
     * 默认算法
     */
    private static final String ALGORITHM_STR = "AES/CBC/PKCS5Padding";

    private static String KEY = "abcdef0123456789"; // 长度必须是 16

    private static String IV = "abcdef0123456789";  // 长度必须是 16

    /**
     * 加密返回的数据转换成 String 类型
     * @param content 明文
     * @param key 秘钥
     * @param iv 初始化向量是16位长度的字符串
     * @return
     * @throws Exception
     */
    public static String encrypt(String content, String key, String iv) throws Exception {
        // 将返回的加密过的 byte[] 转换成Base64编码字符串  ！！！！很关键
        return base64ToString(AES_CBC_Encrypt(content.getBytes(), key.getBytes(), iv.getBytes()));
    }

    /**
     * 将解密返回的数据转换成 String 类型
     * @param content Base64编码的密文
     * @param key 秘钥
     * @param iv 初始化向量是16位长度的字符串
     * @return
     * @throws Exception
     */
    public static String decrypt(String content, String key, String iv) throws Exception {
        // stringToBase64() 将 Base64编码的字符串转换成 byte[]  !!!与base64ToString(）配套使用
        return new String(AES_CBC_Decrypt(stringToBase64(content), key.getBytes(), iv.getBytes()));
    }

    private static byte[] AES_CBC_Encrypt(byte[] content, byte[] keyBytes, byte[] iv){
        try {
            SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE,key, new IvParameterSpec(iv));
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (Exception e) {
            System.out.println("exception:"+e.toString());
        }
        return null;
    }

    private static byte[] AES_CBC_Decrypt(byte[] content, byte[] keyBytes, byte[] iv){
        try {
            SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE,key, new IvParameterSpec(iv));
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (Exception e) {
            System.out.println("exception:"+e.toString());
        }
        return null;
    }

    /**
     * description 解密
     *
     * @param content    1
     * @param decryptKey 2
     * @return java.lang.String
     */
    public static String aesDecode(String content, String decryptKey) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(decryptKey.getBytes(StandardCharsets.UTF_8), "AES");
            //根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance(ALGORITHM_STR);
            //初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(decryptKey.getBytes(StandardCharsets.UTF_8)));
            //8.将加密并编码base64后的字符串内容base64解码成字节数组
            byte[] bytesContent = Base64.decodeBase64(content);
            byte[] byteDecode = cipher.doFinal(bytesContent);
            return new String(byteDecode, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException e) {
            log.error("没有指定的加密算法::" + e.getMessage(), e);
        } catch (IllegalBlockSizeException e) {
            log.error("非法的块大小" + "::" + e.getMessage(), e);
            throw new RuntimeException("密文解密失败");
        } catch (NullPointerException e) {
            log.error("秘钥解析空指针异常" + "::" + e.getMessage(), e);
            throw new RuntimeException("秘钥解析空指针异常");
        } catch (Exception e) {
            log.error("秘钥AES解析出现未知错误" + "::" + e.getMessage(), e);
            throw new RuntimeException("密文解密失败");
        }
        return null;
    }

    /**
     * 字符串装换成 Base64
     */

    public static byte[] stringToBase64(String key) throws Exception {
        return Base64.decodeBase64(key.getBytes());
    }

    /**
     * Base64装换成字符串
     */
    public static String base64ToString(byte[] key) throws Exception {
        return new Base64().encodeToString(key);
    }

}

