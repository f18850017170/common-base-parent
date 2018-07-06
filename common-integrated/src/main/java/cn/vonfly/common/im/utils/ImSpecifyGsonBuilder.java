package cn.vonfly.common.im.utils;

import cn.vonfly.common.im.utils.annotation.FieldAlias;
import cn.vonfly.common.im.utils.annotation.JsonIgnore;
import com.google.gson.*;

import java.lang.reflect.Field;

public class ImSpecifyGsonBuilder {
	private static volatile Gson gson = null;

	/**
	 * 构建定制化IM gosn
	 * @return
	 */
	public static Gson build() {
		if (null == gson) {
			synchronized (ImSpecifyGsonBuilder.class) {
				gson = new GsonBuilder()
						.setFieldNamingStrategy(new FieldNamingStrategy() {
							@Override
							public String translateName(Field f) {
								FieldAlias fieldAlias = f.getAnnotation(FieldAlias.class);
								if (null != fieldAlias) {
									return fieldAlias.value();
								}
								return FieldNamingPolicy.UPPER_CAMEL_CASE.translateName(f);
							}
						}).setExclusionStrategies(new ExclusionStrategy() {
							@Override
							public boolean shouldSkipField(FieldAttributes f) {
								JsonIgnore jsonIgnore = f.getAnnotation(JsonIgnore.class);
								return null != jsonIgnore;
							}

							@Override
							public boolean shouldSkipClass(Class<?> clazz) {
								return false;
							}
						}).create();
			}
		}
		return gson;
	}

}
