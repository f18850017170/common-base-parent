package cn.vonfly.common.im.config;

/**
 * im请求的配置参数
 */
public class ImRequestConfig {
	public static final String SERVICE_NAME_SMS = "sns";// 关系链管理
	public static final String SERVICE_NAME_PROFILE = "profile";// 资料管理
	public static final String SERVICE_NAME_USER = "im_open_login_svc";//账号相关
	private String url;
	private String sdkAppId;
	private String identifier;

	public String getSdkAppId() {
		return sdkAppId;
	}

	public void setSdkAppId(String sdkAppId) {
		this.sdkAppId = sdkAppId;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
