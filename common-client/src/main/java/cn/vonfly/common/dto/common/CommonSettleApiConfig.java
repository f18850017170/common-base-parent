package cn.vonfly.common.dto.common;

/**
 * 通用配置参数
 */
public class CommonSettleApiConfig {
	private String serverUrl;//   请求的服务地址
	private String appKey;//   请求key
	private String appSecret;//   请求serect
	private String appToken;//   请求token
	private String callbackNotifyUrl;//   回调通知地址
	private String publicKeyPath;//   公钥文件路径
	private String privateKeyPath;//   私钥文件路径

	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getAppToken() {
		return appToken;
	}

	public void setAppToken(String appToken) {
		this.appToken = appToken;
	}

	public String getCallbackNotifyUrl() {
		return callbackNotifyUrl;
	}

	public void setCallbackNotifyUrl(String callbackNotifyUrl) {
		this.callbackNotifyUrl = callbackNotifyUrl;
	}

	public String getPublicKeyPath() {
		return publicKeyPath;
	}

	public void setPublicKeyPath(String publicKeyPath) {
		this.publicKeyPath = publicKeyPath;
	}

	public String getPrivateKeyPath() {
		return privateKeyPath;
	}

	public void setPrivateKeyPath(String privateKeyPath) {
		this.privateKeyPath = privateKeyPath;
	}
}
