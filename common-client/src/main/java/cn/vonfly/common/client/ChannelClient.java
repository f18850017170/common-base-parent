package cn.vonfly.common.client;

import cn.vonfly.common.dto.request.AbsBaseRequest;
import cn.vonfly.common.dto.response.AbsBaseResponse;

/**
 * 渠道client
 */
public interface ChannelClient{
	/**
	 * 执行请求
	 * @param request
	 * @return
	 */
	<Req extends AbsBaseRequest<Resp>,Resp extends AbsBaseResponse>Resp execute(Req request);
}
