package cn.vonfly.common.client.annotation;

import java.lang.annotation.*;

/**
 * 渠道标识
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ChannelFlag {
	/**
	 * 渠道code
	 * @return
	 */
	String value();
}
