package cn.vonfly.common.base.exception;

/**
 * 基础信息异常，dubbo消费者捕获到该异常的子类，则可直接返回异常提示信息
 */
public class BaseMsgRuntimeException extends RuntimeException{
	private static final long serialVersionUID = -8256677876843638883L;

	public BaseMsgRuntimeException(String message) {
		super(message);
	}
}
