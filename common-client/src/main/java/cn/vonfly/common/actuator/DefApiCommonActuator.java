package cn.vonfly.common.actuator;

import cn.vonfly.common.client.ChannelClient;
import cn.vonfly.common.client.ChannelClientDispatcher;
import cn.vonfly.common.dto.request.AbsBaseRequest;
import cn.vonfly.common.dto.response.AbsBaseResponse;
import cn.vonfly.common.utils.TipAssert;

/**
 * 执行器默认实现
 */
public class DefApiCommonActuator implements ApiCommonActuator {
	//分发器
	private ChannelClientDispatcher channelClientDispatcher;

	/**
	 * 带参构造函数
	 * @param channelClientDispatcher
	 */
	public DefApiCommonActuator(ChannelClientDispatcher channelClientDispatcher) {
		this.channelClientDispatcher = channelClientDispatcher;
	}

	@Override
	public <Req extends AbsBaseRequest, Resp extends AbsBaseResponse> Resp execute(Req req) {
		TipAssert.notNull(req, "请求不能为null");
		//分发处理渠道
		ChannelClient channelClient = channelClientDispatcher.dispatch(req.getChannelCode());
		TipAssert.notNull(channelClient, req.getChannelCode() + ":渠道client不存在");
		return (Resp) channelClient.execute(req);
	}
}
