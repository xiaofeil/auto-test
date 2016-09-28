package com.xuanru.util;

import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

public class DateUtil {

	private static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

//	private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

	/**
	 * 将指定日期字符串转换为默认格式（yyyy-MM-dd HH:mm:ss）的日期对象
	 * 
	 * @param datetime
	 * @return
	 * @author Liaoxf
	 * @date 2015-8-4 下午4:29:54
	 */
	public static Date parseDateTime(String datetime) {
		if (StringUtils.isEmpty(datetime)) {
			return null;
		}
		try {
			SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATETIME_FORMAT);
			format.setLenient(false);
			return format.parse(datetime);
		} catch (Exception e) {
			// logger.error("", e);
			return null;
		}
	}

	/**
	 * 将指定日期转换为默认格式（yyyy-MM-dd HH:mm:ss）的日期字符串
	 * 
	 * @param datetime
	 * @return
	 * @author Liaoxf
	 * @date 2015-8-4 下午4:29:54
	 */
	public static String parseDateTime(Date datetime) {
		if (datetime == null) {
			return null;
		}
		try {
			SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATETIME_FORMAT);
			format.setLenient(false);
			return format.format(datetime);
		} catch (Exception e) {
			// logger.error("", e);
			return null;
		}
	}

	/**
	 * 计算传入时间与当前时间的间隔分钟数
	 * 
	 * @author liaoxf
	 * @date 2014-9-22
	 * @param clockTime
	 *            格式：yyyy-MM-dd HH:mm
	 * @return
	 */
	public static long calIntervalMinute(String clockTime) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long minutes = 0l;
		try {
			df.setLenient(false);
			df1.setLenient(false);
			Date d1 = df.parse(df1.format(new Date()).substring(0, 16));
			Date d2 = df.parse(clockTime);
			long diff = d1.getTime() - d2.getTime();
			minutes = diff / (1000 * 60);
		} catch (Exception e) {
			// logger.error("", e);
		}
		return Math.abs(minutes);
	}

	/**
	 * 计算传入日期与当前日期的间隔秒数（正数：传入时间在当前时间之前；负数：传入时间在当前时间之后）
	 * 
	 * @param date
	 * @return
	 * @author Liaoxf
	 * @date 2015-8-10 下午7:24:50
	 */
	public static long calIntervalSeconds(Date date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long seconds = 0l;
		try {
			df.setLenient(false);
			df1.setLenient(false);
			Date d1 = df.parse(df1.format(new Date()).substring(0, 19));
			Date d2 = df.parse(df1.format(date).substring(0, 19));
			long diff = d1.getTime() - d2.getTime();
			seconds = diff / 1000;
		} catch (Exception e) {
			// logger.error("", e);
		}
		return seconds;
	}

	/**
	 * 计算指定时间在指定模板格式化后距当前时间的毫秒数
	 * 
	 * @param clockTime
	 *            时间，eg:2014-11-20 14:30:20.987
	 * @param formate
	 *            日期格式，eg:yyyy-MM-dd HH:mm:ss.SSS
	 * @return
	 * @author Liaoxf
	 * @date 2015-8-4 下午4:33:35
	 */
	public static long calIntervalMilliseconds(String clockTime, String formate) {
		DateFormat df = new SimpleDateFormat(formate);
		DateFormat df1 = new SimpleDateFormat(formate);
		long milliseconds = 0l;
		try {
			df.setLenient(false);
			df1.setLenient(false);
			Date d1 = df.parse(df1.format(new Date()));
			Date d2 = df.parse(clockTime);
			long diff = d1.getTime() - d2.getTime();
			milliseconds = diff;
		} catch (Exception e) {
			// logger.error("", e);
		}
		return milliseconds;
	}

	/**
	 * 计算传入日期距当前日期的间隔天数
	 * 
	 * @param date
	 * @return
	 * @author Liaoxf
	 * @date 2015-8-4 下午4:36:34
	 */
	public static long calIntervalDays(String date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
		long days = 0l;
		try {
			df.setLenient(false);
			df1.setLenient(false);
			Date d1 = df.parse(df1.format(new Date()));
			Date d2 = df.parse(date);
			long diff = d1.getTime() - d2.getTime();
			days = diff / (1000 * 60 * 60 * 24);
		} catch (Exception e) {
			// logger.error("", e);
		}
		return days;
	}

	/**
	 * 
	 * desc:计算传入时间与当前时间的间隔秒数(负数：传入时间晚于当前时间；正数：传入时间早于当前时间)
	 * <p>
	 * 创建人：Liaoxf , 2015-10-12 下午12:25:57
	 * </p>
	 * 
	 * @param date
	 * @return
	 */
	public static long calIntervalSeconds(String date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long seconds = 0l;
		try {
			df.setLenient(false);
			df1.setLenient(false);
			Date d1 = df.parse(df1.format(new Date()));
			Date d2 = df.parse(date);
			long diff = d1.getTime() - d2.getTime();
			seconds = diff / 1000;
		} catch (Exception e) {
			// logger.error("", e);
		}
		return seconds;
	}

	/**
	 * 计算两日期间隔天数.若date1早于date2返回负数，date1晚于date2返回正数，相等则返回0
	 * 
	 * @author liaoxf
	 * @date 2014-11-21
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int calIntervalDays(String date1, String date2) {
		// DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int days = 0;
		try {
			// Date d1 = df.parse(date1);
			// Date d2 = df.parse(date2);
			// long diff = d1.getTime() - d2.getTime();
			// days = (int) diff / (1000 * 60 * 60 * 24);
			days = daysBetween(date2, date1);
		} catch (Exception e) {
//			logger.error("", e);
		}
		return days;
	}

	public static int daysBetween(String smdate, String bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(smdate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(bdate));
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	public static int daysBetween(Date smdate, Date bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		smdate = sdf.parse(sdf.format(smdate));
		bdate = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 计算两日期间隔秒数.若date1早于date2返回负数，date1晚于date2返回正数，相等则返回0
	 * 
	 * @author liaoxf
	 * @date 2014-11-21
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long calIntervalSeconds(String date1, String date2) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long seconds = 0l;
		try {
			df.setLenient(false);
			Date d1 = df.parse(date1);
			Date d2 = df.parse(date2);
			long diff = d1.getTime() - d2.getTime();
			seconds = diff / 1000;
		} catch (Exception e) {
//			logger.error("", e);
		}
		return seconds;
	}

	/**
	 * 计算指定日期，指定间隔天数后的日期
	 * 
	 * @author liaoxf
	 * @date 2014-9-22
	 * @param datestr
	 * @param interval
	 * @return
	 * @TODO
	 */
	public static String calDate(String datestr, int interval) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date olddate = null;
		String newDate = "";
		try {
			df.setLenient(false);
			olddate = new Date(df.parse(datestr).getTime());
			Calendar cal = new GregorianCalendar();
			cal.setTime(olddate);

			int Year = cal.get(Calendar.YEAR);
			int Month = cal.get(Calendar.MONTH);
			int Day = cal.get(Calendar.DAY_OF_MONTH);

			int NewDay = Day + interval;

			cal.set(Calendar.YEAR, Year);
			cal.set(Calendar.MONTH, Month);
			cal.set(Calendar.DAY_OF_MONTH, NewDay);

			DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
			newDate = df1.format(cal.getTime());
		} catch (Exception e) {
//			logger.error("", e);
		}
		return newDate;
	}

	/**
	 * 计算当前日期后几天的日期
	 * 
	 * @param days
	 * @return
	 * @author Liaoxf
	 * @date 2015-8-6 上午11:07:09
	 */
	public static Date calDateAfter(int days) {
		Date currDate = null;
		Date newDate = new Date();
		try {
			currDate = new Date();
			Calendar cal = new GregorianCalendar();
			cal.setTime(currDate);

			int Year = cal.get(Calendar.YEAR);
			int Month = cal.get(Calendar.MONTH);
			int Day = cal.get(Calendar.DAY_OF_MONTH);

			int NewDay = Day + days;

			cal.set(Calendar.YEAR, Year);
			cal.set(Calendar.MONTH, Month);
			cal.set(Calendar.DAY_OF_MONTH, NewDay);
			newDate = cal.getTime();
		} catch (Exception e) {
//			logger.error("", e);
		}

		return newDate;
	}

	/**
	 * 
	 * @author liaoxf
	 * @date 2015-4-23
	 * @param datestr
	 *            原始日期
	 * @param interval
	 *            指定月数
	 * @return
	 * @TODO 计算指定月数后的日期
	 */
	public static String calDateByIntervalMonth(String datestr, int interval) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date olddate = null;
		String newDate = "";
		try {
			df.setLenient(false);
			olddate = new Date(df.parse(datestr).getTime());
			Calendar cal = new GregorianCalendar();
			cal.setTime(olddate);
			cal.add(Calendar.MONTH, interval);

			newDate = df.format(cal.getTime());
		} catch (Exception e) {
//			logger.error("", e);
		}
		return newDate;
	}

	/**
	 * 根据指定日期，指定间隔分钟数计算新时间
	 * 
	 * @param datestr
	 *            指定日期
	 * @param interval
	 *            间隔分钟数
	 * @return
	 * @author Liaoxf
	 * @date 2015-8-4 下午4:38:08
	 */
	public static String calDateTimeMinute(String datestr, int interval) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date olddate = null;
		String newDate = "";
		try {
			df.setLenient(false);
			olddate = new Date(df.parse(datestr).getTime());
			Calendar cal = new GregorianCalendar();
			cal.setTime(olddate);

			int Year = cal.get(Calendar.YEAR);
			int Month = cal.get(Calendar.MONTH);
			int Day = cal.get(Calendar.DAY_OF_MONTH);
			int hour = cal.get(Calendar.HOUR_OF_DAY);
			int minute = cal.get(Calendar.MINUTE);
			int second = cal.get(Calendar.SECOND);

			int NewMinute = minute + interval;

			cal.set(Calendar.YEAR, Year);
			cal.set(Calendar.MONTH, Month);
			cal.set(Calendar.DAY_OF_MONTH, Day);
			cal.set(Calendar.HOUR_OF_DAY, hour);
			cal.set(Calendar.MINUTE, NewMinute);
			cal.set(Calendar.SECOND, second);

			DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			newDate = df1.format(cal.getTime());
		} catch (Exception e) {
//			logger.error("", e);
		}
		return newDate;
	}

	/**
	 * 获取当前日期的年月 yyyy-MM
	 * 
	 * @author liaoxf
	 * @date 2014-9-22
	 * @return
	 */
	public static String getCurrentYearMonth() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(new Date()).substring(0, 7);
	}

	/**
	 * 获取当前日期 yyyy-MM-dd
	 * 
	 * @author liaoxf
	 * @date 2015-4-23
	 * @return
	 */
	public static String getCurrentDate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(new Date());
	}

	/**
	 * 
	 * desc:获取当前日期时间 yyyy-MM-dd HH:mm:ss
	 * <p>
	 * 创建人：Liaoxf , 2015-10-12 下午8:05:03
	 * </p>
	 * 
	 * @return
	 */
	public static String getCurrentDateTime() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(new Date());
	}

	/**
	 * 校验年月格式
	 * 
	 * @author liaoxf
	 * @date 2014-9-25
	 * @param date
	 * @return
	 */
	public static boolean checkYearMonth(String date) {
		String eL = "[1-9]{1}[0-9]{3}-[0-9]{2}";
		Pattern p = Pattern.compile(eL);
		Matcher m = p.matcher(date);
		if (m.matches()) {
			return true;
		}
		return false;
	}

	/**
	 * 格式化日期字符串，格式化后样式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 *            待格式化日期字符串
	 * @return
	 * @author Liaoxf
	 * @date 2015-8-4 下午4:41:53
	 */
	public static String pareseDate(String date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			df.setLenient(false);
			return df.format(new Date(df.parse(date).getTime()));
		} catch (ParseException e) {
			// logger.error("", e);
		}
		return null;
	}

	public static String pareseDate(String date, String temp) {
		try {
			if (StringUtil.isNotBlank(date) && StringUtil.isNotBlank(temp)) {
				DateFormat df = new SimpleDateFormat(temp);
				df.setLenient(false);
				return df.format(new Date(df.parse(date).getTime()));
			}
		} catch (ParseException e) {
			// logger.error("", e);
		}
		return null;
	}

	/**
	 * 根据指定日期，指定模板格式化日期。例如：dateToStrByTemplate(new Date(), "yyyyMMddHHmmssSSS")
	 * 返回：20150804164328064
	 * 
	 * @param date
	 *            待格式化日期
	 * @param formatDate
	 *            格式化模板，如：yyyyMMddHHmmssSSS
	 * @return
	 * @author Liaoxf
	 * @date 2015-8-4 下午4:43:40
	 */
	public static String dateToStrByTemplate(Date date, String formatDate) {
		SimpleDateFormat format = new SimpleDateFormat(formatDate);
		format.setLenient(false);
		String str = format.format(date);
		return str;
	}

	public static Date strToDate(String datestr, String formatDate) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat(formatDate);
		df.setLenient(false);
		return new Date(df.parse(datestr).getTime());
	}

	/**
	 * 
	 * desc:根据指定模板格式化日期字符串。
	 * <p>
	 * 创建人：Liaoxf , 2015-12-1 下午5:10:04
	 * </p>
	 * 
	 * @param date
	 *            2015-02-23
	 * @param temp
	 *            如：yyyy-MM-dd
	 * @return 日期字符串与模板匹配返回true，反之，false
	 */
	public static boolean isValidDate(String date, String temp) {
		boolean ret = false;
		try {
			if (StringUtil.isNotBlank(date) && StringUtil.isNotBlank(temp)) {
				SimpleDateFormat df = new SimpleDateFormat(temp);
				df.setLenient(false);
				df.parse(date);
				ret = true;
			}
		} catch (Exception e) {
		}

		return ret;
	}

	public static void main(String[] args) {
		// System.out.println(calIntervalMinute("2014-09-20 11:30"));
		// System.out.println(getCurrentYearMonth());
		// System.out.println(calDate("2014-11-14", -29));
		// System.out.println(calIntervalMilliseconds("2014-11-20 14:30:20:900","yyyy-MM-dd HH:mm:ss:SSS"));
		// System.out.println(calIntervalMilliseconds("2014-11-20 14:30:20.987","yyyy-MM-dd HH:mm:ss.SSS"));
		// System.out.println(checkYearMonth("9114-10"));
		System.out.println(calIntervalDays("2015-10-30 19:03:52", "2015-11-30 19:58:56"));
		System.out.println(calIntervalDays("2015-11-20"));
		// System.out.println(calDateTimeMinute("2014-11-20 14:30:20.987",
		// -30));
		// System.out.println(pareseDate("2014-12-09 14:06:05.080"));
		// System.out.println(calDateByIntervalMonth("2015-3-31", -1));

		// long now = System.currentTimeMillis(); // 排序前取得当前时间
		// Calendar c = Calendar.getInstance();
		// c.setTimeInMillis(now);

		// System.out.println("当前时间: " + c.get(Calendar.YEAR) + "年 " +
		// (c.get(Calendar.MONTH) + 1)
		// + "月 " + c.get(Calendar.DAY_OF_MONTH) + "日  周" +
		// (c.get(Calendar.DAY_OF_WEEK) - 1)
		// + "  " + c.get(Calendar.HOUR_OF_DAY) + "时 " + c.get(Calendar.MINUTE)
		// + "分 "
		// + c.get(Calendar.SECOND) + "秒 " + c.get(Calendar.MILLISECOND) +
		// " 微秒");
		//
		// Date now = new Date();
		// SimpleDateFormat dateFormat = new
		// SimpleDateFormat("yyyyMMddHHmmssSSS");
		// String tablename = dateFormat.format(now);
		// System.out.println(tablename);
		// System.out.println(dateToStrByTemplate(new Date(),
		// "yyyyMMddHHmmssSSS"));
		// System.out.println(calDateAfter(3));
		// System.out.println(System.currentTimeMillis());
		// System.out.println(calIntervalSeconds(parseDateTime("2015-11-19 15:03:18")));
		// System.out.println(calIntervalDays("2015-11-9 19:30:00"));

	}

}
