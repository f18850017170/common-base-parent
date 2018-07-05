package cn.vonfly.common.base.mq.msg;

import java.io.Serializable;

/**
 * mq信息接口
 */
public interface MqInfoMsg extends Serializable{
	/**
	 * 返回队列名称
	 * @return
	 */
	String queueName();
}
