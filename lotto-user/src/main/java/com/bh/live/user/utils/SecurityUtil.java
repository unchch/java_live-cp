package com.bh.live.user.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;


/**
 * 数据加密辅助类(默认编码UTF-8)
 * 
 * @author cgy
 * @since 2011-12-31
 */
public final class SecurityUtil {
	private SecurityUtil() {
	}

	public static final String CHARSET = "UTF-8";

	/**
	 * BASE64解码
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static final byte[] decryptBASE64(String key) {
		try {
			return new BASE64Encoder().decode(key);
		} catch (Exception e) {
			throw new RuntimeException("解密错误，错误信息：", e);
		}
	}

	/**
	 * BASE64编码
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static final String encryptBASE64(byte[] key) {
		try {
			return new BASE64Encoder().encode(key);
		} catch (Exception e) {
			throw new RuntimeException("加密错误，错误信息：", e);
		}
	}


	/**
	 * 基于MD5算法的单向加密 然后再BASE64
	 * 
	 * @param strSrc 明文
	 * @return 返回密文
	 */
	public static final String encryptMd5(String strSrc) {
		String outString = null;
		try {
			outString = encryptBASE64(MDCoder.encodeMD5(strSrc.getBytes(CHARSET)));
		} catch (Exception e) {
			throw new RuntimeException("加密错误，错误信息：", e);
		}
		return outString;
	}

	/**
     * 基于MD5算法的单向加密
     * 
     * @param sourceStr 明文
     * @return 返回密文
     */
	  public static String encryptMd5NoBase64(String sourceStr) {
	        String result = "";
	        try {
	            MessageDigest md = MessageDigest.getInstance("MD5");
	            md.update(sourceStr.getBytes());
	            byte b[] = md.digest();
	            int i;
	            StringBuffer buf = new StringBuffer("");
	            for (int offset = 0; offset < b.length; offset++) {
	                i = b[offset];
	                if (i < 0) {
                        i += 256;
                    }
	                if (i < 16) {
                        buf.append("0");
                    }
	                buf.append(Integer.toHexString(i));
	            }
	            result = buf.toString();
	        } catch (NoSuchAlgorithmException e) {
	            System.out.println(e);
	        }
	        return result;
	    }


	/**
	 * RSA签名
	 *
	 * @param data 原数据
	 * @return
	 */
	public static final String signRSA(String data, String privateKey) {
		try {
			return encryptBASE64(RSACoder.sign(data.getBytes(CHARSET), decryptBASE64(privateKey)));
		} catch (Exception e) {
			throw new RuntimeException("签名错误，错误信息：", e);
		}
	}

	/**
	 * RSA验签
	 *
	 * @param data 原数据
	 * @return
	 */
	public static final boolean verifyRSA(String data, String publicKey, String sign) {
		try {
			return RSACoder.verify(data.getBytes(CHARSET), decryptBASE64(publicKey), decryptBASE64(sign));
		} catch (Exception e) {
			throw new RuntimeException("验签错误，错误信息：", e);
		}
	}

	/**
	 * 数据加密，算法（RSA）
	 *
	 * @param data 数据
	 * @return 加密后的数据
	 */
	public static final String encryptRSAPrivate(String data, String privateKey) {
		try {
			return encryptBASE64(RSACoder.encryptByPrivateKey(data.getBytes(CHARSET), decryptBASE64(privateKey)));
		} catch (Exception e) {
			throw new RuntimeException("加密错误，错误信息：", e);
		}
	}
	/**
	 * 数据加密，算法（RSA）
	 * 
	 * @param data 数据
	 * @return 加密后的数据
	 */
	public static final String encryptRSAPublic(String data, String publicKey) {
	    try {
			return encryptBASE64(RSACoder.encryptByPublicKey(data.getBytes(CHARSET), decryptBASE64(publicKey)));
	    } catch (Exception e) {
	        throw new RuntimeException("加密错误，错误信息：", e);
	    }
	}

	/**
	 * 数据解密，算法（RSA）
	 * 
	 * @param cryptData 加密数据
	 * @return 解密后的数据
	 */
	public static final String decryptRSAPrivate(String cryptData, String privateKey) {
	    try {
	        // 把字符串解码为字节数组，并解密
			return new String(RSACoder.decryptByPrivateKey(decryptBASE64(cryptData), decryptBASE64(privateKey)));
	    } catch (Exception e) {
	        throw new RuntimeException("解密错误，错误信息：", e);
	    }
	}

	public static void main(String[] args) throws Exception {
		//System.out.println(encryptMd5("BHKJ"));
    	Map<String, Object> key = RSACoder.initKey();
		String privateKey = encryptBASE64(RSACoder.getPrivateKey(key));
		String publicKey = encryptBASE64(RSACoder.getPublicKey(key));
		System.out.println("私钥:"+privateKey);
		System.out.println("公钥:"+publicKey);
/*//    	String publicKey = "mfMMdqyjkEzi8L3naqebbqadsMaMsajbaj83fM2QQqp9NotlcBVY+zGNBsTQUU1psErQBj/tWCFTd44dKlBR2xviJpncjlK557qDLXUHIodRLBCHeH1Sed3caMeaaq==";
//		String privateKey = "miibvaibadanb7AG8A9gZMQbaqefaascatUM77eWa7eaaAeaCfMxbLtra+leVmIkxDPVCHgvl6t08EZk8hsuDZpG1D3nRQoUKxvJ4u9QYQiAKVVWbc5/L9KMUpw+w1IsJHyqnMidaqabaAaGDz+hpVtPdeL3xLFM7wV0Cyzl9TluEwhl5lNvBdw+pQU0XwbTp8vk7So730eO5pldymbdaQZ1x8I16QaOG4Pza9eaOWZG8fa2azJWlV8iNFVG+qALeO8qkCm5z8762udj8ruciqc/66oQE9ea8kStf5V+w4ze4Hk7Yq52WQsmnEidIRcHgMi8ajfiG/4UA+HpBzxbtV5I7WP9G8Jge/8h/Lt6C/HTPuyja9bBK3rap9OD+noUEBHWRAcAscyCFQrO9oTSsDkraGUAVMi7scMaJ2g3Sbx3nIZTvY6rpoyOqpS5+JeSIscY0v3tsiy=";
		Map< String, String> c = new HashMap<>();
		c.put("accountName", "111");
		String json = JsonUtil.obj2json(c);
		System.out.println("加密前：   " + json);
//		String sign = signRSA("132", privateKey);
//		System.out.println(sign);
		String encrypt = encryptRSAPublic(json, publicKey);
		System.out.println("加密后：   " + encrypt);
		String org = decryptRSAPrivate(encrypt, privateKey);
		System.out.println("解密后：   " + org);
// 		System.out.println(verifyRSA(org, publicKey, sign));*/
	}
}
