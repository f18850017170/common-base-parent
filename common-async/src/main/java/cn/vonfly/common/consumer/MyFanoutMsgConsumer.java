package cn.vonfly.common.consumer;

import cn.vonfly.common.base.mq.msg.mymsg.MyDelayMsg;
import cn.vonfly.common.base.mq.msg.mymsg.MyFanoutMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MyFanoutMsgConsumer {
	private Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 消息消费者
	 * @param msg
	 */
	@RabbitListener(queues = "#{autoDeleteQueue.name}")
	public void process(MyFanoutMsg msg) {
		logger.info("接到[MyFanoutMsg-autoDeleteQueue]信息msg={}", msg);
		//TODO 业务处理
	}
	@RabbitListener(queues = "#{autoDeleteQueue2.name}")
	public void process2(MyFanoutMsg msg) {
		logger.info("接到[MyFanoutMsg-autoDeleteQueue2]信息msg={}", msg);
		//TODO 业务处理
	}
}
