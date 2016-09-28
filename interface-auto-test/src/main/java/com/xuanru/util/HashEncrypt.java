package com.xuanru.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Md5、SHA1加密工具类
 * 
 * @author chenkaichao
 * @version 1.0, 2012-07-26
 */

public class HashEncrypt {

	private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * 对字符串进行加密
	 * 
	 * @param text
	 *            签名原文
	 * @return 签名后密文
	 */
	public static String doEncrypt(String text, String algorithm, String inputCharset) {
		MessageDigest msgDigest = null;

		try {
			msgDigest = MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(
					"System doesn't support this algorithm.");
		}

		try {
			msgDigest.update(text.getBytes(inputCharset)); // 注意该接口是按照指定编码形式签名
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(
					"System doesn't support your  EncodingException.");
		}

		byte[] bytes = msgDigest.digest();

		String md5Str = new String(encodeHex(bytes));

		return md5Str;
	}
	
	/**
	 * 转成16进制
	 * @param data
	 * @return
	 */
	public static char[] encodeHex(byte[] data) {

		int l = data.length;

		char[] out = new char[l << 1];

		// two characters form the hex value.
		for (int i = 0, j = 0; i < l; i++) {
			out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
			out[j++] = DIGITS[0x0F & data[i]];
		}

		return out;
	}
	public static void main(String[] args) {
		String strTest = "bankAccount=60cd2d3c8d784788b94ff87717898038039c3d72060fb92d&cardHolder=bd1d38d5dc56466e&certCode=aa816e7684b76821b4becbd8d15970969955bf47860f8cb6&certType=01&cvn=f4a83bdc52b4e1ee&expireDate=90fd3f41735f3b1d&instId=20000001&mobileId=18610587857&rpid=WPG1424370000001&trace=1306081413044636&signKey=3s9rgICJaKiet8Ss";
		String signMsg = HashEncrypt.doEncrypt(strTest, "SHA-1", "GBK");
		System.out.println(signMsg);
	}
}