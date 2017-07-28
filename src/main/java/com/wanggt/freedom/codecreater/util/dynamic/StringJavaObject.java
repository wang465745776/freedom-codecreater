package com.wanggt.freedom.codecreater.util.dynamic;

import java.io.IOException;
import java.net.URI;

import javax.tools.SimpleJavaFileObject;

/**
 * Java对象
 * @author freedom wang
 * @date 2017年6月21日下午3:48:54
 * @since 1.0
 */
public class StringJavaObject extends SimpleJavaFileObject {
	/** java源代码 */
	private String code = "";

	/**
	 * 遵循Java规范的类名及文件
	 * @param javaFileName java文件名称
	 * @param code java代码
	 * @author freedom wang
	 * @date 2017年6月21日上午11:30:29
	 * @since 1.0
	 */
	public StringJavaObject(String javaFileName, String code) {
		super(createStringJavaObjectURI(javaFileName), Kind.SOURCE);
		this.code = code;
	}

	/**
	 * 产生一个URL资源路径
	 * @param javaFileName
	 * @return
	 * @author freedom wang
	 * @date 2017年6月21日上午11:30:17
	 * @since 1.0
	 */
	private static URI createStringJavaObjectURI(String javaFileName) {
		// 注意此处未设置包名
		return URI.create("String:///" + javaFileName.replace('.', '/') + Kind.SOURCE.extension);
	}

	/**
	 * 返回文本代码
	 * @param ignoreEncodingErrors
	 * @return
	 * @throws IOException
	 * @author freedom wang
	 * @date 2017年6月21日上午11:30:02
	 * @since 1.0
	 */
	@Override
	public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
		return code;
	}
}
