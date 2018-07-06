package cn.vonfly.common.oss;

/**
 * 文件操作结果
 */
public class FileOperateResult<T> {
	private boolean success;//操作结果
	private String msg;//提示信息
	private T content;//返回内容，如果需要的话

	public FileOperateResult(T content) {
		this.content = content;
		this.success = true;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}
}
