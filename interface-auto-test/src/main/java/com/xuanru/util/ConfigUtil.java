package com.xuanru.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class ConfigUtil {

	/**
	 * Logger for this class
	 */
	private static Logger logger = LoggerFactory.getLogger(ConfigUtil.class);

	/**
	 * 要加载属性文件内容的包路径
	 */
	public static final String configPath = ConfigUtil.class.getClassLoader().getResource("").getPath();

	/**
	 * 要加载应用服务器资源文件的存放路径
	 */
	// public static final String applicationConfigPath =
	// System.getProperty("resin.home") + File.separator
	// + "conf" + File.separator + "wfPay";
	public static final String applicationConfigPath = System.getProperty("catalina.home")
			+ File.separator + "conf" + File.separator + "cdrf-meeting";

	private static Properties prop = new Properties();

	/** 是否实时读取属性文件开关，true:实时读取（优点：无需重启应用服务；缺点：耗资源）；false：从初始化内存中读取 */
	private static boolean actualSwitch = false;

	/**
	 * 不允许实例化 Liaoxf 2015-8-22 上午11:15:56
	 */
	private ConfigUtil() {
		super();
	}

	static {
		loadConfigFiles();
	}

	/**
	 * 
	 * desc:将指定目录下的属性文件key-value加载到内存中
	 * <p>
	 * 创建人：Liaoxf , 2015-8-22 上午10:52:07
	 * </p>
	 */
	private static void loadConfigFiles() {
		try {
			List<File> confFiles = new ArrayList<File>();
//			logger.info(configPath);
			File confDir = new File(configPath);
			if (confDir.exists()) {
				confFiles.addAll(Arrays.asList(confDir.listFiles(new FileFilter() {

					public boolean accept(File pathname) {
						String filename = pathname.getName().toLowerCase();
//						logger.info("confDir filename=" + filename);
						if (filename.endsWith(".properties")) {
							return true;
						} else {
							return false;
						}
					}
				})));
			} else {
				logger.info("本地资源文件路径[" + configPath + "]不存在");
			}

			// 在resin的conf目录下查找该文件
//			File resinConfDir = new File(applicationConfigPath);
//			File files[] = resinConfDir.listFiles();
//			logger.info("resinConfDir files.size=" + files.length);
//			files = confDir.listFiles();
//			logger.info("confDir files.size=" + files.length);
//			if (resinConfDir.exists()) {
//				confFiles.addAll(Arrays.asList(resinConfDir.listFiles(new FileFilter() {
//
//					public boolean accept(File pathname) {
//						String filename = pathname.getName().toLowerCase();
//						logger.info("resinConfDir filename=" + filename);
//						if (filename.endsWith(".properties")) {
//							return true;
//						} else {
//							return false;
//						}
//					}
//				})));
//			} else {
//				logger.info("应用服务器资源文件路径[" + applicationConfigPath + "]不存在");
//			}

			InputStream in = null;
			Properties newProp = new Properties();

			for (File confFile : confFiles) {
				try {
					in = new FileInputStream(confFile);
					newProp.load(in);

				} catch (Exception e) {
					logger.error("", e);
				} finally {
					if (in != null) {
						in.close();
					}
					in = null;
				}
			}

			prop = newProp;
			if (confFiles.size() == 0) {
				logger.info("未找到任何资源文件");
			}
		} catch (Exception ex) {
			logger.error("", ex);
		}
	}

	/**
	 * 
	 * desc:根据属性key查询对应的value值，若找不到key对应的value值，则返回null
	 * <p>
	 * 创建人：Liaoxf , 2015-8-22 上午10:54:06
	 * </p>
	 * 
	 * @param key
	 *            属性key
	 * @return String
	 */
	public static String getProperty(String key) {
		String value = null;
		if (actualSwitch) {
			loadConfigFiles();
		}
		value = prop.getProperty(key);

		return value;
	}

	/**
	 * 
	 * desc:根据属性key值查询对应的value值，若找不到key对应的value值，则将默认值返回
	 * <p>
	 * 创建人：Liaoxf , 2015-8-22 上午10:55:31
	 * </p>
	 * 
	 * @param key
	 *            属性key
	 * @param defaultValue
	 *            找不到属性key对应的value时返回的默认值
	 * @return String
	 */
	public static String getProperty(String key, String defaultValue) {
		String value = getProperty(key);
		return StringUtils.isBlank(value) ? defaultValue : value;
	}

	/**
	 * 
	 * desc:根据属性key值查询对应的value值，若找不到key对应的value值，则将默认值返回
	 * <p>
	 * 创建人：Liaoxf , 2015-8-22 上午10:57:00
	 * </p>
	 * 
	 * @param key
	 *            属性key
	 * @param defaultValue
	 *            找不到属性key对应的value时返回的默认值
	 * @return boolean
	 */
	public static boolean getProperty(String key, boolean defaultValue) {
		String value = getProperty(key);
		return StringUtils.isBlank(value) ? defaultValue : Boolean.parseBoolean(value);
	}

	/**
	 * 
	 * desc:根据属性key值查询对应的value值，若找不到key对应的value值，则将默认值返回
	 * <p>
	 * 创建人：Liaoxf , 2015-8-22 上午10:57:00
	 * </p>
	 * 
	 * @param key
	 *            属性key
	 * @param defaultValue
	 *            找不到属性key对应的value时返回的默认值
	 * @return int
	 */
	public static int getProperty(String key, int defaultValue) {
		String value = getProperty(key);
		return StringUtils.isBlank(value) ? defaultValue : Integer.parseInt(value);
	}

	/**
	 * 
	 * desc:根据属性key值查询对应的value值，若找不到key对应的value值，则将默认值返回
	 * <p>
	 * 创建人：Liaoxf , 2015-8-22 上午10:57:00
	 * </p>
	 * 
	 * @param key
	 *            属性key
	 * @param defaultValue
	 *            找不到属性key对应的value时返回的默认值
	 * @return long
	 */
	public static long getProperty(String key, long defaultValue) {
		String value = getProperty(key);
		return StringUtils.isBlank(value) ? defaultValue : Long.parseLong(value);
	}

	/**
	 * 
	 * desc:根据属性key值查询对应的value值，若找不到key对应的value值，则将默认值返回
	 * <p>
	 * 创建人：Liaoxf , 2015-8-22 上午10:57:00
	 * </p>
	 * 
	 * @param key
	 *            属性key
	 * @param defaultValue
	 *            找不到属性key对应的value时返回的默认值
	 * @return double
	 */
	public static double getProperty(String key, double defaultValue) {
		String value = getProperty(key);
		return StringUtils.isBlank(value) ? defaultValue : Double.parseDouble(value);
	}
}
