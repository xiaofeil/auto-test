package com.xuanru.util;

import net.sf.json.JSONObject;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 操作对象工具类
 * 
 * @author Liaoxf
 * @date 2015-8-5 下午5:22:35
 */
public class ObjectUtil {

	private static Logger logger = LoggerFactory.getLogger(ObjectUtil.class);

	/**
	 * 通过反射机制给javabean赋值(保证JSON串中的key值与javabean对象属性名一致)。
	 * 
	 * @author liaoxf
	 * @date 2015-4-7
	 * @param clazz
	 *            待赋值的Javabean class
	 * @param jsonObj
	 *            值来源JSON对象(暂不支持json嵌套)
	 * @return
	 */
	public static Object setObjectValueFromJson(Class<?> clazz, JSONObject jsonObj) {
		Object obj = null;
		try {
			if (clazz != null && jsonObj != null) {
				obj = clazz.newInstance();
				Field clazzFields[] = clazz.getDeclaredFields();
				Field parentFields[] = clazz.getSuperclass().getDeclaredFields();
				Field fields[] = (Field[]) ArrayUtils.addAll(clazzFields, parentFields);

				String fieldName = "";
				String setMethodName = "";
				Field field = null;
				Object fieldValue = null;
				Method method = null;
				for (int i = 0; i < fields.length; i++) {
					fieldValue = null;
					method = null;
					field = fields[i];
					fieldName = fields[i].getName();
					if (fieldName.length() > 0) {
						setMethodName = "set" + fieldName.substring(0, 1).toUpperCase()
								+ fieldName.substring(1);
						if (jsonObj.has(fieldName)) {
							if (field.getGenericType().toString().indexOf("String") != -1) {
								method = clazz.getDeclaredMethod(setMethodName, String.class);
								fieldValue = jsonObj.getString(fieldName);
							} else if (field.getGenericType().toString().indexOf("Double") != -1) {
								method = clazz.getDeclaredMethod(setMethodName, Double.class);
								fieldValue = jsonObj.getDouble(fieldName);
							} else if (field.getGenericType().toString().indexOf("double") != -1) {
								method = clazz.getDeclaredMethod(setMethodName, double.class);
								fieldValue = jsonObj.getDouble(fieldName);
							} else if (field.getGenericType().toString().indexOf("Long") != -1) {
								method = clazz.getDeclaredMethod(setMethodName, Long.class);
								fieldValue = jsonObj.getLong(fieldName);
							} else if (field.getGenericType().toString().indexOf("long") != -1) {
								method = clazz.getDeclaredMethod(setMethodName, long.class);
								fieldValue = jsonObj.getLong(fieldName);
							} else if (field.getGenericType().toString().indexOf("Integer") != -1) {
								method = clazz.getDeclaredMethod(setMethodName, Integer.class);
								fieldValue = jsonObj.getInt(fieldName);
							} else if (field.getGenericType().toString().indexOf("int") != -1) {
								method = clazz.getDeclaredMethod(setMethodName, int.class);
								fieldValue = jsonObj.getInt(fieldName);
							} else if (field.getGenericType().toString().indexOf("Boolean") != -1) {
								method = clazz.getDeclaredMethod(setMethodName, Boolean.class);
								fieldValue = jsonObj.getBoolean(fieldName);
							} else if (field.getGenericType().toString().indexOf("boolean") != -1) {
								method = clazz.getDeclaredMethod(setMethodName, boolean.class);
								fieldValue = jsonObj.getBoolean(fieldName);
							} else {
								logger.info("fieldName=[" + fieldName + "],type=["
										+ field.getGenericType().toString() + "] is undefined!");
								continue;
							}
							method.invoke(obj, fieldValue);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("setObjectValue exception:", e);
		}
		return obj;
	}

	/**
	 * 对象属性拷贝，拷贝相同属性名称对应的属性值
	 * 
	 * @param sourceObj
	 *            源对象（初始时属性有值）
	 * @param targetObj
	 *            待拷贝的目标对象（初始时属性无值）
	 * @throws Exception
	 * @author Liaoxf
	 * @date 2015-8-5 下午6:52:46
	 */
	public static void copyProperties(Object sourceObj, Object targetObj) throws Exception {
		copyPropertiesExclude(sourceObj, targetObj, null);
	}

	/**
	 * 对象属性拷贝，不复制指定名称的属性值
	 * 
	 * @param sourceObj
	 *            源对象（初始时属性有值）
	 * @param targetObj
	 *            待拷贝的目标对象（初始时属性无值）
	 * @param excludsPropArray
	 *            不拷贝的属性名称数组（注意属性名全小写）
	 * @throws Exception
	 * @author Liaoxf
	 * @date 2015-8-5 下午6:35:52
	 */
	public static void copyPropertiesExclude(Object sourceObj, Object targetObj,
			String[] excludsPropArray) throws Exception {

		List<String> excludesList = null;

		if (excludsPropArray != null && excludsPropArray.length > 0) {

			excludesList = Arrays.asList(excludsPropArray); // 构造列表对象
		}

		Method[] fromMethods = sourceObj.getClass().getDeclaredMethods();
		Method[] toMethods = targetObj.getClass().getDeclaredMethods();
		transferFieldValueExclude(fromMethods, toMethods, sourceObj, targetObj, excludesList);

		// 处理父属性
		fromMethods = sourceObj.getClass().getSuperclass().getDeclaredMethods();
		transferFieldValueExclude(fromMethods, toMethods, sourceObj, targetObj, excludesList);
		toMethods = targetObj.getClass().getSuperclass().getDeclaredMethods();
		transferFieldValueExclude(fromMethods, toMethods, sourceObj, targetObj, excludesList);
	}

	private static void transferFieldValueExclude(Method[] fromMethods, Method[] toMethods,
			Object sourceObj, Object targetObj, List<String> excludesList) throws Exception {
		Method fromMethod = null, toMethod = null;
		String fromMethodName = null, toMethodName = null;

		for (int i = 0; i < fromMethods.length; i++) {

			fromMethod = fromMethods[i];
			fromMethodName = fromMethod.getName();

			if (!fromMethodName.startsWith("get"))
				continue;
			// 排除列表检测
			if (excludesList != null
					&& excludesList.contains(fromMethodName.substring(3).toLowerCase())) {

				continue;
			}
			toMethodName = "set" + fromMethodName.substring(3);
			toMethod = findMethodByName(toMethods, toMethodName);

			if (toMethod == null)
				continue;
			Object value = fromMethod.invoke(sourceObj, new Object[0]);

			if (value == null)
				continue;
			// 集合类判空处理
			if (value instanceof Collection) {

				Collection<?> newValue = (Collection<?>) value;

				if (newValue.size() <= 0)
					continue;
			}

			// 针对java.util.Date类型的数据,在业务层返回时转换成string类型的日期格式.Sep 12, 2015
			// 11:39:56 AM --> 2015-09-12 11:39:56
			if ("java.lang.String".equals(toMethod.getParameterTypes()[0].getName())
					&& (value instanceof Date)) {
				toMethod.invoke(targetObj, new Object[] { DateUtil.parseDateTime((Date) value) });
			}else if ("java.util.Date".equals(toMethod.getParameterTypes()[0].getName())
					&& (value instanceof String)) {
				toMethod.invoke(targetObj, new Object[] { DateUtil.parseDateTime((String) value) });
			} else {
				toMethod.invoke(targetObj, new Object[] { value });
			}
		}
	}

	/**
	 * 对象属性值拷贝，仅复制指定名称的属性值
	 * 
	 * @param sourceObj
	 *            源对象
	 * @param targetObj
	 *            目标对象
	 * @param includsPropArray
	 *            要复制的属性名数组（属性名全小写）
	 * @throws Exception
	 * @author Liaoxf
	 * @date 2015-8-5 下午6:42:28
	 */
	public static void copyPropertiesInclude(Object sourceObj, Object targetObj,
			String[] includsPropArray) throws Exception {

		List<String> includesList = null;

		if (includsPropArray != null && includsPropArray.length > 0) {

			includesList = Arrays.asList(includsPropArray);

		} else {

			return;
		}

		Method[] fromMethods = sourceObj.getClass().getDeclaredMethods();
		Method[] toMethods = targetObj.getClass().getDeclaredMethods();
		transferFieldValueInclude(fromMethods, toMethods, sourceObj, targetObj, includesList);

		// 处理父属性
		fromMethods = sourceObj.getClass().getSuperclass().getDeclaredMethods();
		toMethods = targetObj.getClass().getSuperclass().getDeclaredMethods();
		transferFieldValueInclude(fromMethods, toMethods, sourceObj, targetObj, includesList);
	}

	private static void transferFieldValueInclude(Method[] fromMethods, Method[] toMethods,
			Object sourceObj, Object targetObj, List<String> includesList) throws Exception {
		Method fromMethod = null, toMethod = null;
		String fromMethodName = null, toMethodName = null;

		for (int i = 0; i < fromMethods.length; i++) {

			fromMethod = fromMethods[i];
			fromMethodName = fromMethod.getName();

			if (!fromMethodName.startsWith("get"))
				continue;

			// 排除列表检测
			String str = fromMethodName.substring(3);

			if (!includesList.contains(str.toLowerCase())) {
				continue;
			}

			toMethodName = "set" + fromMethodName.substring(3);
			toMethod = findMethodByName(toMethods, toMethodName);

			if (toMethod == null)
				continue;

			Object value = fromMethod.invoke(sourceObj, new Object[0]);

			if (value == null)
				continue;

			// 集合类判空处理
			if (value instanceof Collection) {

				Collection<?> newValue = (Collection<?>) value;

				if (newValue.size() <= 0)
					continue;
			}

			// 针对java.util.Date类型的数据,在业务层返回时转换成string类型的日期格式.Sep 12, 2015
			// 11:39:56 AM --> 2015-09-12 11:39:56
			if ("java.lang.String".equals(toMethod.getParameterTypes()[0].getName())
					&& (value instanceof Date)) {
				toMethod.invoke(targetObj, new Object[] { DateUtil.parseDateTime((Date) value) });
			}else if ("java.util.Date".equals(toMethod.getParameterTypes()[0].getName())
					&& (value instanceof String)) {
				toMethod.invoke(targetObj, new Object[] { DateUtil.parseDateTime((String) value) });
			} else {
				toMethod.invoke(targetObj, new Object[] { value });
			}
		}
	}

	/**
	 * 从方法数组中获取指定名称的方法
	 * 
	 * @param methods
	 *            方法数组
	 * @param name
	 *            方法名
	 * @return
	 * @author Liaoxf
	 * @date 2015-8-5 下午6:40:45
	 */
	public static Method findMethodByName(Method[] methods, String name) {

		if (methods != null) {
			for (int j = 0; j < methods.length; j++) {

				if (methods[j].getName().equals(name)) {

					return methods[j];
				}

			}
		}
		return null;
	}

}
