package cn.vonfly.common.base.mq.msg.mymsg;

import cn.vonfly.common.base.mq.msg.MqInfoMsg;

import java.util.Date;

public class MySimpleMsg implements MqInfoMsg{
	public static final String QUEUE_NAME="my_simple_msg_queue";
	private String title;
	private String content;
	private Date createTime;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("MySimpleMsg{");
		sb.append("title='").append(title).append('\'');
		sb.append(", content='").append(content).append('\'');
		sb.append(", createTime=").append(createTime);
		sb.append('}');
		return sb.toString();
	}
}
