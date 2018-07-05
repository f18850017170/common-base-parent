package cn.vonfly.common.consumer;

import cn.vonfly.common.base.mq.msg.mymsg.MySimpleMsg;
import com.google.common.collect.Maps;
import com.rabbitmq.client.Channel;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 商户通知消费者
 */
@Component
public class MySimpleMsgConsumer implements InitializingBean {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private Map<String, MySimpleMsgWrapper> rejectMsgMap = Maps.newConcurrentMap();
	//	@Autowired
	//	private SimpMessagingTemplate messagingTemplate;
	//定时线程，处理rejectMsgMap
	private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

	/**
	 * 消息消费者
	 * @param msg
	 */
	@RabbitListener(containerFactory = "manualRabbitListenerContainerFactory",
			queues = MySimpleMsg.QUEUE_NAME)
	public void process(@Payload MySimpleMsg msg, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) {
		logger.info("接到[MySimpleMsg]信息msg={}", msg);

		MySimpleMsgWrapper msgWrapper = new MySimpleMsgWrapper(msg);
		if (!rejectMsgMap.containsKey(msgWrapper.getNotifyMsgKey())) {
			try {
				//TODO 第一次命中的处理
			} catch (Exception e) {
				logger.error("接到[MySimpleMsg]信息msg={}，处理异常", msg, e);
			}
			//第一次命中该节点，直接异常，路由到其他节点
			rejectMsgMap.put(msgWrapper.getNotifyMsgKey(), msgWrapper);
			try {
				channel.basicReject(deliveryTag, true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				channel.basicAck(deliveryTag, false);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				try {
					logger.info("rejectMsgMap定时清理任务开始");
					int num = 0;
					Set<String> keySet = rejectMsgMap.keySet();
					DateTime now = DateTime.now();
					for (String key : keySet) {
						MySimpleMsgWrapper msgWrapper = rejectMsgMap.get(key);
						if (new DateTime(msgWrapper.getMsg().getCreateTime()).plusMinutes(10).isBefore(now)) {
							num++;
							rejectMsgMap.remove(key);
							logger.info("rejectMsgMap定时清理任务，清理消息key={}，msg={}", key, msgWrapper.getMsg());
						}
					}
					logger.info("rejectMsgMap定时清理任务结束,清理size={}", num);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 0, 10, TimeUnit.MINUTES);
	}

	/**
	 *	消息包装类
	 * @param
	 */
	public static class MySimpleMsgWrapper implements Serializable {
		private String notifyMsgKey;//消息key
		private MySimpleMsg msg;//消息内容
		private boolean isRead;//是否已读
		private Date expireTime;//过期时间

		public MySimpleMsgWrapper(MySimpleMsg msg) {
			this.notifyMsgKey = msg.getTitle() + msg.getContent();
			this.msg = msg;
		}

		/**
		 * 读确认
		 */
		public void readConfirm() {
			this.isRead = true;
			this.expireTime = new Date();
		}

		/**
		 * 是否已过期
		 * @return
		 */
		public boolean expired() {
			if (isRead) {
				DateTime expiredTime = new DateTime(this.expireTime).plus(5 * 60 * 1000);
				return expiredTime.isAfterNow();//过期时间还未到达
			}
			return false;
		}

		public String getNotifyMsgKey() {
			return notifyMsgKey;
		}

		public void setNotifyMsgKey(String notifyMsgKey) {
			this.notifyMsgKey = notifyMsgKey;
		}

		public boolean isRead() {
			return isRead;
		}

		public void setRead(boolean read) {
			isRead = read;
		}

		public MySimpleMsg getMsg() {
			return msg;
		}

		public void setMsg(MySimpleMsg msg) {
			this.msg = msg;
		}

		public Date getExpireTime() {
			return expireTime;
		}

		public void setExpireTime(Date expireTime) {
			this.expireTime = expireTime;
		}
	}
}
