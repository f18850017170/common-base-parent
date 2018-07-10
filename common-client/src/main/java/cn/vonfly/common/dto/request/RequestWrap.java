package cn.vonfly.common.dto.request;

/**
 * 请求包装类
 */
public interface RequestWrap<NReq, NResp> {
	/**
	 * 获取请求实体
	 * @return
	 */
	NReq getRequest();

	/**
	 * 获取返回结果类
	 * @return
	 */
	Class<NResp> getResponseClass();
}
