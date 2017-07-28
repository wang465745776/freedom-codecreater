package com.wanggt.freedom.codecreater.annotation;

import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 代码生成参数注解
 * @author freedom wang
 * @date 2017年6月21日下午11:00:06
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ FIELD })
@Documented
public @interface CodeCreaterParam {

	/**
	 * 参数名称
	 * @return
	 * @author freedom wang
	 * @date 2017年6月21日下午10:53:52
	 * @since 1.0
	 */
	public String value() default "";

}
