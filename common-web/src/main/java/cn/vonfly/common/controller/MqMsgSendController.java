package cn.vonfly.common.controller;

import cn.vonfly.common.base.mq.CommonMqInfoSender;
import cn.vonfly.common.base.mq.msg.mymsg.MyDelayMsg;
import cn.vonfly.common.base.mq.msg.mymsg.MyFanoutMsg;
import cn.vonfly.common.base.mq.msg.mymsg.MySimpleMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("mq/")
public class MqMsgSendController {
	@Autowired
	private CommonMqInfoSender commonMqInfoSender;

	/**
	 * 简单消息
	 * @return
	 */
	@RequestMapping("simple/{title}/{content}")
	public Boolean simpleMsg(@PathVariable("title") String title,@PathVariable("content") String content){
		MySimpleMsg msg = new MySimpleMsg();
		msg.setCreateTime(new Date());
		msg.setContent(content);
		msg.setTitle(title);
		commonMqInfoSender.send(msg);
		return true;
	}

	/**
	 * 延迟消息
	 * @return
	 */
	@RequestMapping("delay/{title}/{content}/{delay}")
	public Boolean delayMsg(@PathVariable("title") String title,@PathVariable("content") String content,@PathVariable("delay") Long delay){
		MyDelayMsg msg = new MyDelayMsg();
		msg.setDelayTime(delay);
		msg.setContent(content);
		msg.setTitle(title);
		commonMqInfoSender.sendDelay(msg);
		return true;
	}

	/**
	 * 广播消息
	 * @return
	 */
	@RequestMapping("fanout/{title}/{content}")
	public Boolean fanoutMsg(@PathVariable("title") String title,@PathVariable("content") String content){
		MyFanoutMsg msg = new MyFanoutMsg();
		msg.setContent(content);
		msg.setTitle(title);
		commonMqInfoSender.sendFanout(msg);
		return true;
	}
}
