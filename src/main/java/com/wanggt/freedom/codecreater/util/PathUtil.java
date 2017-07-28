package com.wanggt.freedom.codecreater.util;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;

/**
 * 路径工具类
 * @author freedom wang
 * @date 2017年5月11日下午3:55:59
 * @since 1.0
 */
public class PathUtil {

	/**
	 * 获取项目根类路径
	 * @return
	 * @author freedom wang
	 * @date 2017年6月21日下午2:57:16
	 * @since 1.0
	 */
	public static String getClassesRootPath() {
		File file = null;
		try {
			file = new File(PathUtil.class.getResource("/").toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return file.getAbsolutePath();
	}

	/**
	 * 获取资源的输入流.<br>
	 * 
	 * <b>使用介绍：</b><br>
	 * 获取跟类相对路径的资源.第一个参数为跟资源相对的类，第二个参数为资源相对于类的路径.<br>
	 * 注意：相对路径不需要使用./\等为前缀表示相对路径，但是使用了也不会影响使用
	 * @param clazz
	 * @param filePath
	 * @return
	 * @author freedom wang
	 * @date 2017年5月11日下午3:29:56
	 * @since 1.0
	 */
	public static InputStream getResource(Class<?> clazz, String filePath) {
		if (filePath.startsWith("/") || filePath.startsWith("\\")) {
			filePath = filePath.substring(1);
			return getResource(clazz, filePath);
		}

		return clazz.getResourceAsStream(filePath);
	}

	/**
	 * 获取资源的路径.<br>
	 * 
	 * <b>使用介绍：</b><br>
	 * 获取跟类相对路径的资源.第一个参数为跟资源相对的类，第二个参数为资源相对于类的路径.<br>
	 * 注意：相对路径不需要使用./\等为前缀表示相对路径，但是使用了也不会影响使用
	 * @param clazz
	 * @param filePath
	 * @return
	 * @author freedom wang
	 * @date 2017年5月11日下午3:29:56
	 * @since 1.0
	 */
	public static String getResourcePath(Class<?> clazz, String filePath) throws URISyntaxException {
		if (filePath.startsWith("/") || filePath.startsWith("\\")) {
			filePath = filePath.substring(1);
			return getResourcePath(clazz, filePath);
		}

		File file = new File(clazz.getResource(filePath).toURI());

		return file.getAbsolutePath();
	}

	/**
	 * 此方法获取系统根目录
	 * @return
	 * @author freedom wang
	 * @date 2017年5月11日下午4:12:11
	 * @since 1.0
	 */
	public static String getProjectRootPath() {
		return System.getProperty("user.dir");
	}
}
