package cn.vonfly.config;

import cn.vonfly.common.base.mq.CommonMqInfoSender;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqSenderConfig {
	/**
	 * 构建通用的消息发送者
	 * @param amqpTemplate
	 * @return
	 */
	@Bean
	public CommonMqInfoSender build(AmqpTemplate amqpTemplate){
		return new CommonMqInfoSender(amqpTemplate);
	}

}
