package cn.vonfly.common.client;

/**
 * 渠道client分发器
 */
public interface ChannelClientDispatcher {
	/**
	 * 根据渠道code分发对应渠道的渠道client
	 * @param channelCode
	 * @return
	 */
	ChannelClient dispatch(String channelCode);
}
