package cn.vonfly.common.websocket.repo;

import java.util.Set;

/**
 * 在线商户仓库
 */
public interface UserOnlineRepo {
	/**
	 * 增加
	 * @param userId
	 * @param sessionId
	 * @return
	 */
	String put(Long userId, String sessionId);

	/**
	 * 移除
	 * @param sessionId
	 * @return
	 */
	Long remove(String sessionId);

	/**
	 * 移除
	 * @param userId
	 * @return
	 */
	Set<String> remove(Long userId);
	/**
	 * 根据商户Id获取sessionId
	 * @param userId
	 * @return
	 */
	Set<String> get(Long userId);
}
