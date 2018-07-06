package cn.vonfly.common.im.sig;

/**
 * 用户签名信息
 */
public class UserSigItem {
	private long sdkAppId;//所属appId(申请时获得)
	private String identifier;//用户身份标识
	private String userSig;//签名信息
	private long initTimestamps;//创建签名时的时间戳
	private long expireTimestamps;//签名过期时的时间戳

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getUserSig() {
		return userSig;
	}

	public void setUserSig(String userSig) {
		this.userSig = userSig;
	}

	public long getInitTimestamps() {
		return initTimestamps;
	}

	public void setInitTimestamps(long initTimestamps) {
		this.initTimestamps = initTimestamps;
	}

	public long getExpireTimestamps() {
		return expireTimestamps;
	}

	public void setExpireTimestamps(long expireTimestamps) {
		this.expireTimestamps = expireTimestamps;
	}

	public long getSdkAppId() {
		return sdkAppId;
	}

	public void setSdkAppId(long sdkAppId) {
		this.sdkAppId = sdkAppId;
	}
}
