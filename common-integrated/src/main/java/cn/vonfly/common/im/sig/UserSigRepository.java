package cn.vonfly.common.im.sig;

/**
 * 用户签名库
 */
public interface UserSigRepository {
	/**
	 * 获取
	 * @param identifier
	 * @return
	 */
	UserSigItem getBy(String identifier);

	/**
	 * 直接添加，已存在则覆盖
	 * @param identifier
	 * @param item
	 * @return
	 */
	UserSigItem add(String identifier, UserSigItem item);
	/**
	 * 更新(存在时更新成功)
	 * @param identifier
	 * @param item
	 * @return
	 */
	boolean replace(String identifier, UserSigItem item);

	/**
	 * 添加（不存在时添加成功）
	 * @param identifier
	 * @param item
	 * @return
	 */
	boolean addIfAbsent(String identifier, UserSigItem item);

	/**
	 * 清除
	 * @return
	 */
	void clear();
}
