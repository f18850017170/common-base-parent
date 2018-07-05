package cn.vonfly.common.consumer;

import cn.vonfly.common.base.mq.msg.mymsg.MyDelayMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MyDelayMsgConsumer {
	private Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 消息消费者
	 * @param msg
	 */
	@RabbitListener(queues = MyDelayMsg.QUEUE_NAME)
	public void process(MyDelayMsg msg) {
		logger.info("接到[MyDelayMsg]信息msg={}", msg);
		//TODO 业务处理
	}
}
