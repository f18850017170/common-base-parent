package cn.vonfly.common.actuator;

import cn.vonfly.common.dto.request.AbsBaseRequest;
import cn.vonfly.common.dto.response.AbsBaseResponse;

/**
 * api请求统一执行器
 */
public interface ApiCommonActuator {
	/**
	 * 执行请求
	 */
	<Req extends AbsBaseRequest, Resp extends AbsBaseResponse> Resp execute(Req req);
}
