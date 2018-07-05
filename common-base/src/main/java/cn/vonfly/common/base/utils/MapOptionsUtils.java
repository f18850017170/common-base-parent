package cn.vonfly.common.base.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class MapOptionsUtils {
	/**
	 * 集合转换为map
	 * @param collection
	 * @param function
	 * @param <K>
	 * @param <I>
	 * @return
	 */
	public static <K, I> Map<K, I> listConvert2Map(Collection<I> collection, Function<I, K> function) {
		Map<K, I> result = new HashMap<>();
		if (null != collection) {
			collection.forEach(item -> result.put(function.apply(item), item));
		}
		return result;
	}
}
