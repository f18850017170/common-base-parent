package cn.vonfly.common.im.sig;
/*
用户签名管理器
 */
public interface UserSigManager {
	/**
	 * 获取或构建可用的用户签名
	 * @param sdkAppId
	 * @param identifier
	 * @return
	 */
	UserSigItem getOrGenAvailable(long sdkAppId, String identifier);
}
