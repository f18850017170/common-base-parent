package cn.vonfly.common.base.mq.msg.mymsg;

import cn.vonfly.common.base.mq.msg.FanoutMqMsg;

public class MyFanoutMsg implements FanoutMqMsg{
	public static final String FANOUT_EXCHANGE="my_msg";
	private String title;
	private String content;
	@Override
	public String fanoutExchange() {
		return FANOUT_EXCHANGE;
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

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("MyFanoutMsg{");
		sb.append("title='").append(title).append('\'');
		sb.append(", content='").append(content).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
