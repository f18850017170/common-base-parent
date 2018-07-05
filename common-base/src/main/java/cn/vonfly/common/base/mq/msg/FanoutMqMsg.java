package cn.vonfly.common.base.mq.msg;

import java.io.Serializable;

public interface FanoutMqMsg extends Serializable{
	String FANOUT_EXCHANGE_SUFFIX = "_fanout_exchange";//默认广播交换机名称

	/**
	 * 交换机名称
	 * @return
	 */
	String fanoutExchange();
}
