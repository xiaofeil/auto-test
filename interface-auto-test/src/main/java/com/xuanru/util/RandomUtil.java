package com.xuanru.util;

import java.util.Random;

/**
 * 随机数生成工具类
 * @author chenkaichao
 * @version 1.0 2011-05-22
 */
public class RandomUtil {
	
	/**
	 * 得到随机数字
	 * @param length 长度
	 * @return 随机数
	 */
	public static String getRandomNum(int length){
		return getRandom(0, length);
	}
	
	/**
	 * 得到随机小写字母
	 * @param length 长度
	 * @return 随机数
	 */
	public static String getRandomLowerLetter(int length){
		return getRandom(1, length);
	}
	/**
	 * 得到随机大写字母
	 * @param length 长度
	 * @return 随机数
	 */
	public static String getRandomUpperLetter(int length){
		return getRandom(2, length);
	}
	/**
	 * 得到随机数（数字+小写字母）
	 * @param length 长度
	 * @return 随机数
	 */
	public static String getRandomNumAndLowLetter(int length){
		return getRandom(3, length);
	}
	/**
	 * 得到随机数（数字+大写字母）
	 * @param length 长度
	 * @return 随机数
	 */
	public static String getRandomNumAndUpperLetter(int length){
		return getRandom(4, length);
	}
	/**
	 * 得到随机数（大写字母+小写字母）
	 * @param length 长度
	 * @return 随机数
	 */
	public static String getRandomLowAndUpperLetter(int length){
		return getRandom(5, length);
	}
	/**
	 * 得到随机数（数字+大小写字母）
	 * @param length 长度
	 * @return 随机数
	 */
	public static String getRandomNumAndLetter(int length){
		return getRandom(6, length);
	}
	
	/**
	 * 得到指定类型和长度的随机数
	 * @param type 随机数类型 0：数字，1：小写字母，2：大写字母，3：数字+小写字母，
	 * 						4：数字+大写字母，5 小写字母+大写字母，6：数字+小写字母+大写字母
	 * @param length 随机数的长度
	 * @return 随机数
	 */
	public static String getRandom(int type, int length) {
		StringBuffer st = new StringBuffer("");
		switch (type) {
		case 0:
			Random rd = new Random();
			double d = rd.nextDouble();
			String s = String.valueOf(d).substring(2);
			if (s.length() < length) {
				st.append(s);
				for (int i = 0; i < length - s.length(); i++) {
					st.append(rd.nextInt(10));
				}
			} else {
				st.append(s.substring(0, length));
			}
			break;
		case 1:
			char[] str1 = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
					'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
					'w', 'x', 'y', 'z' };
			st.append(genRandomByChar(str1, length));
			break;
		case 2:
			char[] str2 = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
					'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
					'W', 'X', 'Y', 'Z' };
			st.append(genRandomByChar(str2, length));
			break;
		case 3:
			char[] str3 = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
					'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
					'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7',
					'8', '9' };
			st.append(genRandomByChar(str3, length));
			break;
		case 4:
			char[] str4 = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
					'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
					'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7',
					'8', '9' };
			st.append(genRandomByChar(str4, length));
			break;
		case 5:
			char[] str5 = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
					'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
					'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
					'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
					'u', 'v', 'w', 'x', 'y', 'z' };
			st.append(genRandomByChar(str5, length));
			break;
		case 6:
			char[] str6 = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
					'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
					'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
					'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
					'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5',
					'6', '7', '8', '9' };
			st.append(genRandomByChar(str6, length));
			break;
		default:

			break;
		}

		return st.toString();

	}

	/**
	 * 得到制定字符数组的指定长度的随机字符串
	 * 
	 * @param str
	 *            源字符数组
	 * @param length
	 *            得到随机字符串的长度
	 * @return 随机字符串
	 */
	public static String genRandomByChar(char[] str, int length) {
		if (str == null) {
			return "";
		}

		StringBuffer st = new StringBuffer("");
		int strlen = str.length;
		int i; // 生成的随机数
		int count = 0; // 生成的随机数的长度

		Random r = new Random();
		while (count < length) {
			// 生成随机数，取绝对值，防止生成负数，
			i = Math.abs(r.nextInt(strlen)); // 生成的数最大为strlen对应的数字
			if (i >= 0 && i < strlen) {
				st.append(str[i]);
				count++;
			}
		}
		return st.toString();
	}

	public static void main(String[] args) {
		System.out.println(getRandom(0, 10));
		System.out.println(getRandom(0, 150));
		System.out.println(getRandom(1, 4));
		System.out.println(getRandom(1, 143));
		System.out.println(getRandom(2, 6));
		System.out.println(getRandom(2, 133));
		System.out.println(getRandom(3, 6));
		System.out.println(getRandom(3, 33));
		System.out.println(getRandom(4, 6));
		System.out.println(getRandom(4, 44));
		System.out.println(getRandom(5, 8));
		System.out.println(getRandom(5, 66));
		System.out.println(getRandom(6, 5));
		System.out.println(getRandom(6, 54));
		System.out.println("["+getRandom(7, 77)+"]");

	}

}
