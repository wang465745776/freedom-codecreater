package com.wanggt.freedom.codecreater.core.observer;

import com.wanggt.freedom.codecreater.core.GlobalOperateCenter;

/**
 * 实现了此接口的类，可以获取到全局操作中心对象
 * @author freedom wang
 * @date 2017年7月24日下午1:53:16
 * @since 1.0
 */
public interface HasGlobalOperate {

	/**
	 * 设置代码生成器里定义的全局操作中心
	 * @param operates
	 * @author freedom wang
	 * @date 2017年7月24日下午1:53:32
	 * @since 1.0
	 */
	void setGlobalOperateCenter(GlobalOperateCenter globalOperateCenter);
}
