package cn.vonfly.common.im.sig;

import com.google.common.collect.Maps;

import java.util.concurrent.ConcurrentMap;

/**
 * 内存存储
 */
public class MemoryUserSigRepository implements UserSigRepository {
	private final ConcurrentMap<String, UserSigItem> map = Maps.newConcurrentMap();

	@Override
	public UserSigItem getBy(String identifier) {
		return map.get(identifier);
	}

	@Override
	public UserSigItem add(String identifier, UserSigItem item) {
		return map.put(identifier,item);
	}

	@Override
	public boolean replace(String identifier, UserSigItem item) {
		return  null != map.replace(identifier, item);
	}

	@Override
	public boolean addIfAbsent(String identifier, UserSigItem item) {
		return null != map.putIfAbsent(identifier,item);
	}

	@Override
	public void clear() {
		map.clear();
	}
}
