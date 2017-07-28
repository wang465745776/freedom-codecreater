package com.wanggt.freedom.codecreater.core.observer;

import java.util.List;

import com.wanggt.freedom.codecreater.core.operate.Operate;

/**
 * 实现了此接口的标记类，在代码生成器上注册时可以获取到代码生成器上注册的所有操作对象
 * @author freedom wang
 * @date 2017年6月23日下午2:48:00
 * @since 1.0
 */
public interface HasOperate {

	/**
	 * 设置代码生成器里注册的操作对象
	 * @param operates
	 * @author freedom wang
	 * @date 2017年6月23日下午2:07:49
	 * @since 1.0
	 */
	void setOperates(List<Operate> operates);
}
