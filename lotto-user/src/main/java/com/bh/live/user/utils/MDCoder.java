
package com.bh.live.user.utils;

import java.security.MessageDigest;

/**
 * MD加密组件
 * 
 * @author cgy
 * @version 1.0
 * @since 1.0
 */
public abstract class MDCoder {

	/**
	 * MD2加密
	 * 
	 * @param data 待加密数据
	 * @return byte[] 消息摘要
	 * @throws Exception
	 */
	public static byte[] encodeMD2(byte[] data) throws Exception {
		// 初始化MessageDigest
		MessageDigest md = MessageDigest.getInstance("MD2");
		// 执行消息摘要
		return md.digest(data);
	}

	/**
	 * MD4加密
	 * 
	 * @param data 待加密数据
	 * @return byte[] 消息摘要
	 * @throws Exception
	 */
	public static byte[] encodeMD4(byte[] data) throws Exception {
		// 初始化MessageDigest
		MessageDigest md = MessageDigest.getInstance("MD4");
		// 执行消息摘要
		return md.digest(data);
	}

	/**
	 * MD5加密
	 * 
	 * @param data 待加密数据
	 * @return byte[] 消息摘要
	 * @throws Exception
	 */
	public static byte[] encodeMD5(byte[] data) throws Exception {
		// 初始化MessageDigest
		MessageDigest md = MessageDigest.getInstance("MD5");
		// 执行消息摘要
		return md.digest(data);
	}

	/**
	 * Tiger加密
	 * 
	 * @param data 待加密数据
	 * @return byte[] 消息摘要
	 * @throws Exception
	 */
	public static byte[] encodeTiger(byte[] data) throws Exception {
		// 初始化MessageDigest
		MessageDigest md = MessageDigest.getInstance("Tiger");
		// 执行消息摘要
		return md.digest(data);
	}


	/**
	 * Whirlpool加密
	 * 
	 * @param data 待加密数据
	 * @return byte[] 消息摘要
	 * @throws Exception
	 */
	public static byte[] encodeWhirlpool(byte[] data) throws Exception {
		// 初始化MessageDigest
		MessageDigest md = MessageDigest.getInstance("Whirlpool");
		// 执行消息摘要
		return md.digest(data);
	}



	/**
	 * GOST3411加密
	 * 
	 * @param data 待加密数据
	 * @return byte[] 消息摘要
	 * @throws Exception
	 */
	public static byte[] encodeGOST3411(byte[] data) throws Exception {
		// 初始化MessageDigest
		MessageDigest md = MessageDigest.getInstance("GOST3411");
		// 执行消息摘要
		return md.digest(data);
	}

}
