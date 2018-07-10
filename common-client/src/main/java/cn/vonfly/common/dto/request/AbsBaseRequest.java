package cn.vonfly.common.dto.request;

import cn.vonfly.common.dto.response.AbsBaseResponse;
import cn.vonfly.common.utils.GsonBuilderUtils;
import cn.vonfly.common.utils.SimpleReflectUtils;

/**
 *	基础请求类
 */
public abstract class AbsBaseRequest<R extends AbsBaseResponse> {
	//请求的渠道编码
	private String channelCode;

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	/**
	 * 返回对应的返回实体
	 * @return
	 */
	public Class<R> responseClass() {
		return (Class<R>) SimpleReflectUtils.getGenericParameterType(getClass(), AbsBaseResponse.class)[0];
	}

	/**
	 * 打印请求信息
	 * @return
	 */
	public String printContent() {
		return GsonBuilderUtils.gson().toJson(this);
	}

	/**
	 * 构建失败返回内容
	 * @param stackTraceAsString
	 * @return
	 */
	public R buildFail(String stackTraceAsString) {
		Class<R> responseClass = responseClass();
		R instance = null;
		try {
			instance = responseClass.newInstance();
			instance.setCode(AbsBaseResponse.DEF_EXCEPTION_CODE);
			instance.setMsg(stackTraceAsString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return instance;
	}
}
