package cn.vonfly.common.im.dto.response;

import org.apache.commons.lang.StringUtils;

/**
 * 基础信息
 */
public abstract class BaseApiResponse {
	public static final String ACTION_STATUS_OK = "OK";
	public static final String ACTION_STATUS_FAIL = "FAIL";
	private String actionStatus = ACTION_STATUS_OK;//请求处理的结果，“OK” 表示处理成功，“FAIL” 表示失败。
	private Integer errorCode;//错误码。
	private String errorInfo;//详细错误信息。
	private String errorDisplay;//详细的客户端展示信息。
	private String responseBody;//返回的字符串内容

	/**
	 * 是否请求ok
	 * @return
	 */
	public boolean isActionOk(){
		return StringUtils.equals(ACTION_STATUS_OK,getActionStatus());
	}
	public String getActionStatus() {
		return actionStatus;
	}

	public void setActionStatus(String actionStatus) {
		this.actionStatus = actionStatus;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

	public String getErrorDisplay() {
		return errorDisplay;
	}

	public void setErrorDisplay(String errorDisplay) {
		this.errorDisplay = errorDisplay;
	}

	public String getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}
}
