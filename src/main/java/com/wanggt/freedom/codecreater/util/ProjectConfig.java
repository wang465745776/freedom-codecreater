package com.wanggt.freedom.codecreater.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 项目配置类
 * @author tang freedom
 * @date 2017年1月10日下午3:13:24
 * @version 1.0
 */
public class ProjectConfig {
	/** 日志对象 */
	private static Logger logger = LoggerFactory.getLogger(ProjectConfig.class);

	/** 项目配置 */
	private static Properties projectProperties;

	/** 用户自定义的项目配置 */
	private static Properties customProperties;

	static {
		InputStream projectConfigFile = ProjectConfig.class.getResourceAsStream("/codecreater.properties");

		projectProperties = new Properties();
		try {
			projectProperties.load(projectConfigFile);
		} catch (IOException e) {
			logger.error("项目配置加载失败,原因是：" + e.getMessage());
		}
	}

	/**
	 * 设置用户自定义的项目配置
	 * @param customProperties
	 * @author freedom wang
	 * @date 2017年6月25日上午9:56:55
	 * @since 1.0
	 */
	public static void setCustomProperties(Properties customProperties) {
		ProjectConfig.customProperties = customProperties;
	}

	/**
	 * 调用此方法获取系统配置
	 * @param key
	 * @return
	 * @throws IOException
	 * @author tang freedom
	 * @date 2017年1月10日下午3:19:06
	 * @since 1.0
	 */
	public static String getProperty(String key) {
		if (projectProperties != null) {
			String value = projectProperties.getProperty(key);
			String customValue = null;

			if (customProperties != null) {
				customValue = customProperties.getProperty(key);
			}

			return customValue != null ? customValue : value;
		} else {
			logger.error("项目配置加载失败！");
			return null;
		}
	}
}
