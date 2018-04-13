package com.wanggt.freedom.codecreater.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Test;

import com.wanggt.freedom.codecreater.core.symbol.CodeCreaterTestBean;

public class CodeCreaterTest {

	@Test
	public void testCreateCodeInputStreamProperties() throws IOException {
		InputStream resourceAsStream = CodeCreaterTest.class.getResourceAsStream("/CodeCreaterTemplate.template");

		InputStream propertiesStream = CodeCreaterTest.class.getResourceAsStream("/CodeCreaterTemplate.properties");
		Properties properties = new Properties();
		properties.load(propertiesStream);

		CodeCreaterTestBean bean = new CodeCreaterTestBean();
		bean.setAuthor("freedom");

		bean.setStudents(new Student[] { new Student("freedom"), new Student("Tom"), new Student("Job") });
		bean.setStudent(new Student("王大叔"));

		// 创建一个代码生成器
		CodeCreater codeCreater = new CodeCreater();

		codeCreater.createCode(resourceAsStream, new Properties[] { properties }, new Object[] { bean });

		resourceAsStream.close();
		propertiesStream.close();
	}

}
