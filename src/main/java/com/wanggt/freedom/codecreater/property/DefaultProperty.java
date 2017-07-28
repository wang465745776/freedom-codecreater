package com.wanggt.freedom.codecreater.property;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.wanggt.freedom.codecreater.util.ProjectConfig;

public class DefaultProperty {

	/**
	 * 调用此方法获取默认的配置信息
	 * @return
	 * @throws IOException
	 * @author freedom wang
	 * @date 2017年6月21日上午9:39:01
	 * @since 1.0
	 */
	public static Properties getConfig() throws IOException {
		// 获取默认配置的路径
		String defaultConfigFile = ProjectConfig.getProperty("defaultConfigFile");

		// 获取默认配置的流，并获取到其中的配置信息
		FileInputStream fileInputStream = new FileInputStream(defaultConfigFile);
		Properties properties = new Properties();
		properties.load(fileInputStream);
		fileInputStream.close();

		return properties;
	}
}
