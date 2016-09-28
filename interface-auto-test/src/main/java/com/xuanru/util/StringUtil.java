package com.xuanru.util;

import java.util.StringTokenizer;

/**
 * 处理字符串工具类
 * 
 * @author Liaoxf
 * @date 2015-8-5 下午5:29:00
 */
public class StringUtil {

	public static String toString(Object o) {
		if (o != null)
			return o.toString();
		return "";
	}

	public static boolean isBlank(String s) {
		return org.apache.commons.lang.StringUtils.isBlank(s);
	}

	public static boolean isNotBlank(String s) {
		return org.apache.commons.lang.StringUtils.isNotBlank(s);
	}

	public static boolean isEmpty(String s) {
		return org.apache.commons.lang.StringUtils.isEmpty(s);
	}

	public static boolean isNotEmpty(String s) {
		return org.apache.commons.lang.StringUtils.isNotEmpty(s);
	}

	public static boolean isIntegerBlank(Integer i) {
		if (i == null || isBlank(i.toString())) {
			return true;
		}
		return false;
	}
	
	public static boolean isIntegerNotBlank(Integer i) {
		if (i != null && isNotBlank(i.toString())) {
			return true;
		}
		return false;
	}

	public static boolean isDoubleBlank(Double d) {
		if (d == null || isBlank(d.toString())) {
			return true;
		}
		return false;
	}

	public static boolean isDoubleNotBlank(Double d) {
		if (d != null && isNotBlank(d.toString())) {
			return true;
		}
		return false;
	}

	/**
	 * 将给定的字符串按照给定的分隔符分割成一个数组
	 * 
	 * @param strToSplit
	 *            要处理的字符串
	 * @param separator
	 *            分隔符
	 * @return 分解后形成的数组
	 */
	public static String[] splitString2Array(String strToSplit, String separator) throws Exception {
		if (strToSplit == null || strToSplit.length() == 0)
			return null;
		if (separator == null || separator.length() == 0)
			return null;
		if (strToSplit.startsWith(separator)) // 如果是以分隔符打头，则将打头的分隔符去掉
		{
			strToSplit = strToSplit.substring(separator.length());
		}
		int count = 0;
		int index = 0;
		int lastIndex = strToSplit.lastIndexOf(separator);
		if (lastIndex + separator.length() < strToSplit.length()) // 如果不是以分割符结尾则补一个
		{
			strToSplit += separator;
		}
		for (int pos = 0; (pos = strToSplit.indexOf(separator, index)) > 0;) {
			count++;
			index = pos + separator.length();
		}

		if (count == 0)
			return (new String[] { strToSplit });
		String retStrArray[] = new String[count];
		index = 0;
		int pos;
		for (int i = 0; (pos = strToSplit.indexOf(separator, index)) > 0; i++) {
			retStrArray[i] = strToSplit.substring(index, pos);
			index = pos + separator.length();
		}
		return retStrArray;
	}

	/**
	 * v1 大于等于 v2时，返回true;小于时，返回false。
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static boolean versionCompare(String v1, String v2) {
		// if v1 or v2 is null retun false
		if (org.apache.commons.lang.StringUtils.isBlank(v1)
				|| org.apache.commons.lang.StringUtils.isBlank(v2)) {
			return false;
		}
		String[] _v1 = v1.split("\\.");
		String[] _v2 = v2.split("\\.");

		double ver1 = Double.valueOf(v1.replace(".", ""));
		double ver2 = Double.valueOf(v2.replace(".", ""));
		if (ver1 == ver2) {
			return true;
		} else {
			for (int i = 0; i < _v1.length; i++) {
				if (_v2.length <= i) {
					return true;
				}

				if (Integer.parseInt(_v1[i]) > Integer.parseInt(_v2[i])) {
					return true;
				} else if (Integer.parseInt(_v1[i]) < Integer.parseInt(_v2[i])) {
					return false;
				}
			}
		}
		return false;
	}

	/**
	 * 模糊化指定字符串，如手机号，银行账户等 186****6789
	 * 
	 * @param strContent
	 *            待模糊化字符串
	 * @param before
	 *            字符串前面明文位数
	 * @param after
	 *            字符串尾部明文位数
	 * @return
	 * @author Liaoxf
	 * @date 2015-8-5 下午5:32:25
	 */
	public static String fuzzifyStr(String strContent, int before, int after) throws Exception {
		String ret = "";
		if (StringUtil.isNotBlank(strContent) && strContent.length() > (before + after)) {
			int n = strContent.length() - (before + after);
			ret = strContent.substring(0, before);
			for (int i = 0; i < n; i++) {
				ret += "*";
			}
			ret += strContent.substring(strContent.length() - after);
		}
		return ret;
	}

	// 按照splitor分割src
	public static String[] splitString(String src, String splitor) {
		StringTokenizer s = new StringTokenizer(src, splitor);

		String[] strs = new String[s.countTokens()];
		int i = 0;
		while (s.hasMoreTokens()) {
			strs[i++] = s.nextToken();
		}

		return strs;
	}

	/**
	 * 
	 * desc:将指定字符串按照指定长度在源字符串之前补齐指定字符。例如：polishStr("11",4,"0") --> "0011"
	 * <p>
	 * 创建人：Liaoxf , 2015-10-31 下午12:12:23
	 * </p>
	 * 
	 * @param str
	 *            源字符串
	 * @param len
	 *            补齐后长度
	 * @param len
	 *            补齐字符
	 * @return
	 */
	public static String polishStr(String str, int len, String polishStr) {
		String newStr = "";
		if (isNotBlank(polishStr) && isNotBlank(str) && str.length() <= len) {
			int count = len - str.length();
			String zeroStr = "";
			for (int i = 0; i < count; i++) {
				zeroStr += polishStr;
			}
			newStr = zeroStr + str;
		}
		return newStr;
	}
}
