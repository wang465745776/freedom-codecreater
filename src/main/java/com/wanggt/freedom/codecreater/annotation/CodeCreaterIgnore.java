package com.wanggt.freedom.codecreater.annotation;

import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 此注解为标记注解，标记某字段不作为代码生成的参数
 * @author freedom wang
 * @date 2017年6月21日下午11:00:06
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ FIELD })
@Documented
public @interface CodeCreaterIgnore {
}
