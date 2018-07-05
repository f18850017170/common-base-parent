package cn.vonfly.config;

import cn.vonfly.common.base.mq.msg.mymsg.MyDelayMsg;
import cn.vonfly.common.base.mq.msg.mymsg.MyFanoutMsg;
import cn.vonfly.common.base.mq.msg.mymsg.MySimpleMsg;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqQueueConfig {
	@Autowired
	private ConnectionFactory connectionFactory;

	/**
	 * mq队列
	 * @return
	 */
	@Bean
	public Queue mySimpleQueue() {
		return new Queue(MySimpleMsg.QUEUE_NAME);
	}

	/**
	 * 手动确认的消息容器
	 * @return
	 */
	@Bean
	public SimpleRabbitListenerContainerFactory manualRabbitListenerContainerFactory() {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setConcurrentConsumers(1);
		factory.setMaxConcurrentConsumers(1);
		factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
		return factory;
	}

	/**
	 *创建TTL缓冲队列
	 * @return
	 */
	@Bean
	public Queue myDelayMessageTTLBufferedQueue(Queue myDelayMessageProcessQueue) {
		return QueueBuilder.durable(myDelayMessageProcessQueue.getName() + MyDelayMsg.DELAY_QUEUE_SUFFIX)
				//如果队列设置了Dead Letter Exchange（DLX），
				// 那么这些Dead Letter就会被重新publish到Dead Letter Exchange，通过Dead Letter Exchange路由到其他队列
				.withArgument("x-dead-letter-exchange", "")//绑定dead letter发送到的exchange
				.withArgument("x-dead-letter-routing-key", myDelayMessageProcessQueue.getName())//指定dead latter发送到的队列
				.build();
	}

	/**
	 * 延迟消息处理队列
	 * @return
	 */
	@Bean
	public Queue myDelayMessageProcessQueue() {
		return new Queue(MyDelayMsg.QUEUE_NAME);
	}

	/**
	 * 创建交换机
	 * @return
	 */
	@Bean
	public FanoutExchange myMyFanoutExchange() {
		return new FanoutExchange(MyFanoutMsg.FANOUT_EXCHANGE + MyFanoutMsg.FANOUT_EXCHANGE_SUFFIX);
	}

	/**
	 * 匿名队列
	 * @param myMyFanoutExchange
	 * @return
	 */
	@Bean
	public Queue autoDeleteQueue(FanoutExchange myMyFanoutExchange){
		return new AnonymousQueue();
	}

	@Bean
	public Queue autoDeleteQueue2() {
		return new AnonymousQueue();
	}
	/**
	 * 绑定
	 * @param autoDeleteQueue
	 * @param myMyFanoutExchange
	 * @return
	 */
	@Bean
	public Binding  binding(Queue autoDeleteQueue,FanoutExchange myMyFanoutExchange){
		return BindingBuilder.bind(autoDeleteQueue).to(myMyFanoutExchange);
	}
	/**
	 * 绑定
	 * @param autoDeleteQueue2
	 * @param myMyFanoutExchange
	 * @return
	 */
	@Bean
	public Binding  binding2(Queue autoDeleteQueue2,FanoutExchange myMyFanoutExchange){
		return BindingBuilder.bind(autoDeleteQueue2).to(myMyFanoutExchange);
	}
}
