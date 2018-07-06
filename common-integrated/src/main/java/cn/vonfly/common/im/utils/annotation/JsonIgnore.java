package cn.vonfly.common.im.utils.annotation;

import java.lang.annotation.*;

/**
 * 序列化忽略字段
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonIgnore {
}
