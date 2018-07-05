package cn.vonfly.common.base.mq.msg.mymsg;

import cn.vonfly.common.base.mq.msg.DelayMqMsg;

public class MyDelayMsg implements DelayMqMsg {
	public static final String QUEUE_NAME = "my_delay_msg_queue";
	private String title;
	private String content;
	private long delayTime;
	@Override
	public long getDelayMilliSeconds() {
		return delayTime;
	}

	@Override
	public String queueName() {
		return QUEUE_NAME;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getDelayTime() {
		return delayTime;
	}

	public void setDelayTime(long delayTime) {
		this.delayTime = delayTime;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("MyDelayMsg{");
		sb.append("title='").append(title).append('\'');
		sb.append(", content='").append(content).append('\'');
		sb.append(", delayTime=").append(delayTime);
		sb.append('}');
		return sb.toString();
	}
}
