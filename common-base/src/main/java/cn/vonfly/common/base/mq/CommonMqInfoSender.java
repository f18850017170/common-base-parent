package cn.vonfly.common.base.mq;

import cn.vonfly.common.base.mq.msg.DelayMqMsg;
import cn.vonfly.common.base.mq.msg.FanoutMqMsg;
import cn.vonfly.common.base.mq.msg.MqInfoMsg;
import cn.vonfly.common.base.utils.TipAssert;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;

/**
 * 通用mq信息发送者
 */
public class CommonMqInfoSender {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private AmqpTemplate amqpTemplate;

	public CommonMqInfoSender(AmqpTemplate amqpTemplate) {
		this.amqpTemplate = amqpTemplate;
	}

	/**
	 * 发送信息
	 * @param msg
	 * @param <M>
	 */
	public <M extends MqInfoMsg> void send(M msg) {
		try {
			TipAssert.isTrue(null != msg && StringUtils.isNotBlank(msg.queueName()), "消息队列或消息不能为空");
			amqpTemplate.convertAndSend(msg.queueName(), msg);
			logger.info("信息发送至mq-成功，queueName={},msg={}", msg.queueName(), msg);
		} catch (Exception e) {
			logger.error("信息发送至mq-异常，queueName={},msg={}", null != msg ? msg.queueName() : "msg为空", msg, e);
		}
	}

	/**
	 * 发送延迟消息
	 * @param msg
	 * @param <M>
	 */
	public <M extends DelayMqMsg> void sendDelay(M msg) {
		try {
			TipAssert.isTrue(null != msg && StringUtils.isNotBlank(msg.queueName()), "消息队列或消息不能为空");
			String routingKey = msg.queueName() + M.DELAY_QUEUE_SUFFIX;
			amqpTemplate.convertAndSend(routingKey, msg, (message -> {
				message.getMessageProperties().setExpiration(msg.getDelayMilliSeconds() + "");
				return message;
			}));
			logger.info("信息发送至delay-mq-成功，queueName={},msg={},delayMilliSeconds={},delayQueueName={}", msg.queueName(),
					msg, msg.getDelayMilliSeconds(), routingKey);
		} catch (Exception e) {
			String queueName = null != msg ? msg.queueName() : "msg为空";
			logger.error("信息发送至delay-mq-异常，queueName={},msg={},delayMilliSeconds={},delayQueueName={}",
					queueName, msg, msg.getDelayMilliSeconds(), queueName + M.DELAY_QUEUE_SUFFIX, e);
		}
	}

	/**
	 * 发送广播信息
	 * @param msg
	 * @param <M>
	 */
	public <M extends FanoutMqMsg> void sendFanout(M msg) {
		String exchange = null;
		try {
			exchange = msg.fanoutExchange() + M.FANOUT_EXCHANGE_SUFFIX;
			TipAssert.isTrue(null != msg, "消息不能为空");
			amqpTemplate.convertAndSend(exchange, "", msg);
			logger.info("信息发送至fanout-mq成功，exchange={}，msg={}", exchange, msg);
		} catch (Exception e) {
			logger.error("信息发送至fanout-mq异常，exchange={}，msg={}，异常信息：", exchange, msg, e);
		}
	}
}
