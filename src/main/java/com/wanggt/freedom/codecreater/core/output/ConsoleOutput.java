package com.wanggt.freedom.codecreater.core.output;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 控制台输出，此输出为默认输出
 * @author freedom wang
 * @date 2017年6月25日上午11:33:41
 * @since 1.0
 */
public class ConsoleOutput implements Output {
	private static Logger logger = LoggerFactory.getLogger(ConsoleOutput.class);

	@Override
	public void output(String code) {
		logger.info("解析完的代码为：");
		logger.info(code);
	}
}
