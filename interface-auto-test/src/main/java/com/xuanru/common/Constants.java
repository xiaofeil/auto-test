package com.xuanru.common;

import java.io.File;

/**
 * 
 * desc:系统常量
 * <p>
 * 创建人：Liaoxf 创建日期：2015-12-6
 * </p>
 * 
 * @version V1.0
 */
public class Constants {

	public static final String CHARSET_UTF_8 = "UTF-8";

	public static final String SUCCESS = "0000";

	public static final String FAIL = "-1000";

	public static final String NICKNAME_REGEX = "^[\\u4E00-\\u9FA50-9A-Za-z`~@#\\$\\^()\\+=:;'/•·\\{\\}\\[\\]\\|\\-\\._]{1,}$";

	public static final String MOBILE_REGEX = "^(1[3|4|5|7|8])[0-9]{9}$";

	public static final String POSTCODE_REGEX = "^[0-9]{6}$";

	public static final String MAIL_REGEX = "^[\\-\\._0-9A-Za-z]+@\\w+(\\.\\w+)*\\.(com|co|net|org|cn|me|biz|com.cn|net.cn|org.cn|gov.cn|name|info|so|tel|mobi|asia|cc|tv)$";

	public static final String BANKCARDNO_REGEX = "^[0-9]{15,19}$";

	public static final String SPLIT = "@";

	public static final String SUBMEETING_SPLIT = ";"; // 分会场分隔符

	public static final String OFFICE_EXCEL_2003_POSTFIX = "xls";

	public static final String OFFICE_EXCEL_2010_POSTFIX = "xlsx";

	public static final String POINT = ".";

	public static final String EXCLE_UPLOAD_PATH = System.getProperty("user.dir")
			+ File.separator
			+ "cdrf-meeting"
			+ File.separator
			+ "upload" + File.separator;

	public static final String IMAGE_UPLOAD_PATH = System.getProperty("user.dir")
			+ File.separator
			+ "cdrf-meeting"
			+ File.separator
			+ "image" + File.separator;

	public static final int PAGE_SIZE = 10; // 分页查询时默认每页查询记录数

	public static final String DATE_TEMPLATE = "yyyyMMddHHmmssSSS"; // 文件根据当前时间命名模板
}
