package cn.vonfly.common.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 简单反射工具
 */
public class SimpleReflectUtils {
	/**
	 * 获取泛型参数
	 * @param targetClass
	 * @param superClass
	 * @return
	 */
	public static Type[] getGenericParameterType(Class<?> targetClass, Class<?> superClass) {
		Type genericSuperclass = targetClass.getGenericSuperclass();
		while (!(genericSuperclass instanceof ParameterizedType) && !genericSuperclass.equals(superClass)) {
			genericSuperclass = ((Class) genericSuperclass).getGenericSuperclass();
		}
		TipAssert.isTrue(null != genericSuperclass && genericSuperclass instanceof ParameterizedType,
				superClass.getSimpleName()+"实现类，未指定泛型的真实类型");
		ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
		Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
		return actualTypeArguments;
	}
}
