package com.wanggt.freedom.codecreater.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 输入流输出流工具类
 * @author freedom wang
 * @date 2017年6月22日下午8:59:50
 * @since 1.0
 */
public class IOUtil {

	/**
	 * 提取输入流中的字符串
	 * @param inputStream
	 * @return
	 * @throws IOException
	 * @author freedom wang
	 * @date 2017年6月21日上午9:52:41
	 * @since 1.0
	 */
	public static String getString(InputStream inputStream) throws IOException {
		InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");

		StringBuffer code = new StringBuffer();
		char[] buffer = new char[1024];
		int length = -1;
		while ((length = reader.read(buffer)) != -1) {
			for (int i = 0; i < length; i++) {
				code.append(buffer[i]);
			}
		}
		reader.close();

		return code.toString();
	}
}
