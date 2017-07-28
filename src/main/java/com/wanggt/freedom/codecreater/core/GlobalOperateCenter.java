package com.wanggt.freedom.codecreater.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wanggt.freedom.codecreater.core.operate.Operate;

/**
 * 全局操作中心
 * @author freedom wang
 * @date 2017年7月24日下午2:28:02
 * @since 1.0
 */
public class GlobalOperateCenter {

	/** 全局操作中心 */
	private Map<String, List<Operate>> globalOperate;

	/**
	 * 新增一个全局操作
	 * @param param 参数名
	 * @param operate 操作对象
	 * @author freedom wang
	 * @date 2017年7月24日下午2:22:18
	 * @since 1.0
	 */
	public void addGlobalOperate(String param, Operate operate) {
		if (globalOperate == null) {
			globalOperate = new HashMap<String, List<Operate>>();

			List<Operate> operates = new ArrayList<Operate>();
			operates.add(operate);

			globalOperate.put(param, operates);
		} else {
			List<Operate> operates = globalOperate.get(param);

			if (operates != null) {
				operates.add(operate);
			} else {
				operates = new ArrayList<Operate>();
				operates.add(operate);

				globalOperate.put(param, operates);
			}
		}
	}

	/**
	 * 获取针对某参数的全局操作参数
	 * @param param 参数名称
	 * @return 若不存在则返回null
	 * @author freedom wang
	 * @date 2017年7月24日下午2:24:33
	 * @since 1.0
	 */
	public List<Operate> getGlobalOperate(String param) {
		List<Operate> operates = null;

		if (globalOperate != null) {
			operates = globalOperate.get(param);
		}

		return operates;
	}

	/**
	 * 获取全局操作参数
	 * @return 若不存在则返回null
	 * @author freedom wang
	 * @date 2017年7月24日下午2:27:18
	 * @since 1.0
	 */
	public Map<String, List<Operate>> getGlobalOperate() {
		return globalOperate;
	}
}
