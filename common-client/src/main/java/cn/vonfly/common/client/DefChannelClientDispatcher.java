package cn.vonfly.common.client;

import cn.vonfly.common.client.annotation.ChannelFlag;
import cn.vonfly.common.utils.TipAssert;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * 渠道client分发器，默认实现
 */
public class DefChannelClientDispatcher implements ChannelClientDispatcher {
	//具体渠道map
	private final Map<String, ChannelClient> channelClientMap = Maps.newHashMap();

	/**
	 * 带参构造函数
	 * @param channelClientList
	 */
	public DefChannelClientDispatcher(List<ChannelClient> channelClientList) {
		if (null != channelClientList && !channelClientList.isEmpty()) {
			channelClientList.forEach(channelClient -> {
				ChannelFlag flag = channelClient.getClass().getAnnotation(ChannelFlag.class);
				if (null != flag && StringUtils.isNotBlank(flag.value())) {
					channelClientMap.put(flag.value(), channelClient);
				}
			});

		}
	}

	@Override
	public ChannelClient dispatch(String channelCode) {
		TipAssert.hasText(channelCode, "渠道请求分发失败，渠道编码(channelCode)不能为空");
		return channelClientMap.get(channelCode);
	}
}
