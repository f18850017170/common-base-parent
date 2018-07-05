package cn.vonfly.common.base.mq.msg;

public interface DelayMqMsg extends MqInfoMsg {
	/**
	 * 延迟缓冲队列后缀
	 */
	String DELAY_QUEUE_SUFFIX = "_delay_ttl_buffered";
	/**
	 * 获取消息的延迟毫秒数
	 * @return
	 */
	long getDelayMilliSeconds();
}
