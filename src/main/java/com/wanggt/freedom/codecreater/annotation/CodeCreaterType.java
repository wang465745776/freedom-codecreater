package com.wanggt.freedom.codecreater.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 此注解为标记注解，被标记的类表示其属性都作为代码生成的参数
 * @author freedom wang
 * @date 2017年6月21日下午11:00:06
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@Documented
public @interface CodeCreaterType {
}
