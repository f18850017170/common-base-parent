package cn.vonfly.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonBuilderUtils {
	private final static Gson gson = new GsonBuilder().create();

	public static Gson gson() {
		return gson;
	}

	private GsonBuilderUtils() {
	}
}
