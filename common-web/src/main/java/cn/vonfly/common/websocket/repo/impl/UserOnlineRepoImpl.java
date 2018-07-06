package cn.vonfly.common.websocket.repo.impl;

import cn.vonfly.common.websocket.repo.UserOnlineRepo;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
public class UserOnlineRepoImpl implements UserOnlineRepo {
	private final Map<Long, Set<String>> userSessionMap = Maps.newConcurrentMap();
	private final Map<String, Long> sessionUserMap = Maps.newConcurrentMap();

	@Override
	public String put(Long userId, String sessionId) {
		if (null != userId) {
			synchronized (userId + "") {
				Set<String> sessionSets = userSessionMap.get(userId);
				if (null == sessionSets) {
					sessionSets = Sets.newConcurrentHashSet();
					userSessionMap.put(userId, sessionSets);
				}
				sessionSets.add(sessionId);
				sessionUserMap.put(sessionId, userId);
			}
		}
		return sessionId;
	}

	@Override
	public Long remove(String sessionId) {
		Long merchantId = sessionUserMap.get(sessionId);
		if (null != merchantId) {
			synchronized (merchantId + "") {
				Set<String> sessionSets = userSessionMap.get(merchantId);
				sessionSets.remove(sessionId);
				sessionUserMap.remove(sessionId);
			}
		}
		return merchantId;
	}

	@Override
	public Set<String> remove(Long userId) {
		Set<String> sessionSets = null;
		if (null != userId) {
			synchronized (userId) {
				sessionSets = userSessionMap.get(userId);
				if (null != sessionSets) {
					sessionSets.forEach(s -> sessionUserMap.remove(s));
				}
			}
		}
		return sessionSets;
	}

	@Override
	public Set<String> get(Long userId) {
		return userSessionMap.get(userId);
	}
}
