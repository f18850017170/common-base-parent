package cn.vonfly.common.im.sig;

/**
 * 用户签名生成器
 */
public interface UserSigGenerator {
	/**
	 * 构建用户签名
	 * @param sdkAppId
	 * @param identifier
	 * @return
	 */
	UserSigItem generate(long sdkAppId, String identifier) throws Exception;

	/**
	 * 根据用户签名解析出 签名生成的信息
	 * @param userSig
	 * @param sdkAppId
	 * @param identifier
	 * @return
	 */
	UserSigItem userSigAnalyze(String userSig, long sdkAppId, String identifier) throws Exception;

	/**
	 * 是否过期（与当前时间比较）
	 * @param item
	 * @return
	 */
	boolean isExpired(UserSigItem item);
}
