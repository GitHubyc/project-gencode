package com.xakj.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 多组合bean转Map集合
 */
public class ParamBeanToMap {

	/**
	 * 多组合bean转Map集合
	 */
	public static Map<String, Object> paramBeanToMap(Object bean) {
		try {
			@SuppressWarnings("rawtypes")
			Class type = bean.getClass();
			Map<String, Object> returnMap = new HashMap<String, Object>();
			BeanInfo beanInfo = Introspector.getBeanInfo(type);

			PropertyDescriptor[] propertyDescriptors = beanInfo
					.getPropertyDescriptors();
			for (int i = 0; i < propertyDescriptors.length; i++) {
				PropertyDescriptor descriptor = propertyDescriptors[i];
				String propertyName = descriptor.getName();
				System.out.println(propertyName);
				if (!propertyName.equals("class")) {
					Method readMethod = descriptor.getReadMethod();
					Object result = readMethod.invoke(bean, new Object[0]);
					if (result != null) {
						returnMap.putAll(beanToMap(result, propertyName));
					}
				}
			}
			return returnMap;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 对象转Map集合
	 */
	public static Map<String, Object> beanToMap(Object bean, String paramName) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (bean == null)
				return null;
			BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo
					.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				if (key.compareToIgnoreCase("class") == 0) {
					continue;
				}
				Method getter = property.getReadMethod();
				Object value = getter != null ? getter.invoke(bean) : null;
				map.put(paramName + "." + key, value);
			}

			return map;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		return map;
	}
}
