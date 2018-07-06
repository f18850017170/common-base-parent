package cn.vonfly.common.im.utils.annotation;

import java.lang.annotation.*;

/**
 * 字段别名
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldAlias {
	String value();
}
