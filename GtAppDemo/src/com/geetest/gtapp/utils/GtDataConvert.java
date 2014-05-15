package com.geetest.gtapp.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据类型转换
 * @author Zheng
 * 2014年5月15日  上午11:24:07
 */
public class GtDataConvert {
	
//	
//	/**
//     * 将一个 JavaBean 对象转化为一个  Map
//     * @param bean 要转化的JavaBean 对象
//     * @return 转化出来的  Map 对象
//     * @throws IntrospectionException 如果分析类属性失败
//     * @throws IllegalAccessException 如果实例化 JavaBean 失败
//     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
//     */
//    @SuppressWarnings({ "rawtypes", "unchecked" })
//	public static Map convertBean(Object bean)
//            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
//        Class type = bean.getClass();
//        Map returnMap = new HashMap();
//        BeanInfo beanInfo = Introspector.getBeanInfo(type);
//
//        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
//        for (int i = 0; i< propertyDescriptors.length; i++) {
//            PropertyDescriptor descriptor = propertyDescriptors[i];
//            String propertyName = descriptor.getName();
//            if (!propertyName.equals("class")) {
//                Method readMethod = descriptor.getReadMethod();
//                Object result = readMethod.invoke(bean, new Object[0]);
//                if (result != null) {
//                    returnMap.put(propertyName, result);
//                } else {
//                    returnMap.put(propertyName, "");
//                }
//            }
//        }
//        return returnMap;
//    }
	
	
	
	
	
	
}
