package cn.vonfly.common.dto.response;

import org.apache.commons.lang3.StringUtils;

/**
 * 基础返回类
 */
public abstract class AbsBaseResponse {
	public static final String DEF_EXCEPTION_CODE="500";
	public static final String DEF_SUCCESS_CODE = "0";
	public static final String DEF_BIZ_SUCCESS_CODE = "0";
	protected String code;
	protected String msg;
	protected String bizCode;
	protected String bizMsg;

	/**
	 * 是否接口请求成功
	 * @return
	 */
	public boolean isSuccess() {
		return StringUtils.equals(DEF_SUCCESS_CODE, code);
	}

	/**
	 * 是否业务成功
	 * @return
	 */
	public boolean isBizSuccess() {
		return StringUtils.equals(DEF_BIZ_SUCCESS_CODE, bizCode);
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public String getBizMsg() {
		return bizMsg;
	}

	public void setBizMsg(String bizMsg) {
		this.bizMsg = bizMsg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
