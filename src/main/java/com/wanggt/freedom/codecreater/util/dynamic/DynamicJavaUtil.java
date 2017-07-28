package com.wanggt.freedom.codecreater.util.dynamic;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.wanggt.freedom.codecreater.util.ProjectConfig;

/**
 * 动态Java工具类,此工具类不能定义成静态类型，否则动态加载的类将不会被回收，相同类名的时候，动态加载将使用以前加载的类，返回预料之外的值<br>
 * 而且建议保证动态加载的类名不一致，以防止GC未回收相同类名的类
 * @author freedom wang
 * @date 2017年6月21日下午3:57:01
 * @since 1.0
 */
public class DynamicJavaUtil {

	/**
	 * 动态加载Java类，并执行其中的某个方法
	 * @param className
	 * @param code
	 * @param methodName
	 * @param parameters
	 * @return
	 * @throws Exception
	 * @author freedom wang
	 * @date 2017年6月21日下午3:49:30
	 * @since 1.0
	 */
	public Object dynamic(String className, String code, String methodName, Object[] parameters) throws Exception {
		// 动态加载类，并返回Class对象
		Class<?> findClass = DynamicClassLoader.findClass(className, code);

		// 创建一个动态加载类的对象
		Object newInstance = findClass.newInstance();

		// 获取需要执行的方法
		List<Class<?>> list = new ArrayList<Class<?>>();
		for (Object oneParameter : parameters) {
			list.add(oneParameter.getClass());
		}
		Method method = findClass.getMethod(methodName, list.toArray(new Class<?>[0]));

		// 执行方法，并返回值
		Object value = method.invoke(newInstance, parameters);

		// 删除类文件
		DynamicClassLoader.deleteClass(className);
		return value;
	}

	/**
	 * 调用此方法是用动态Java方式执行一条语句，默认加载类{@link ParamParser}
	 * @param code
	 * @return
	 * @throws Exception
	 * @author freedom wang
	 * @date 2017年6月22日上午11:29:25
	 * @since 1.0
	 */
	public Object dynamicOneStatement(String code) throws Exception {
		String className = "DynamicTempJava" + new Date().getTime();

		StringBuffer codeBuffer = new StringBuffer();
		// 定义包名
		codeBuffer.append("package com.wanggt.freedom.codecreater.util.dynamic;").append(
				ProjectConfig.getProperty("newline"));
		codeBuffer.append(ProjectConfig.getProperty("newline"));

		// 定义导入包
		codeBuffer.append("import com.wanggt.freedom.codecreater.param.ParamParser;").append(
				ProjectConfig.getProperty("newline"));
		codeBuffer.append(ProjectConfig.getProperty("newline"));

		// 类定义
		codeBuffer.append("public class " + className + " {").append(ProjectConfig.getProperty("newline"));
		codeBuffer.append(ProjectConfig.getProperty("newline"));

		// 方法定义
		codeBuffer.append(ProjectConfig.getProperty("tab"));
		codeBuffer.append("public Object dealOneStatement() throws IllegalArgumentException, IllegalAccessException {");
		codeBuffer.append(ProjectConfig.getProperty("newline"));

		codeBuffer.append(ProjectConfig.getProperty("tab")).append(ProjectConfig.getProperty("tab"));
		codeBuffer.append("return ").append(code).append(";");
		codeBuffer.append(ProjectConfig.getProperty("newline"));

		codeBuffer.append(ProjectConfig.getProperty("tab")).append("}").append(ProjectConfig.getProperty("newline"));

		codeBuffer.append("}").append(ProjectConfig.getProperty("newline"));

		return dynamic("com.wanggt.freedom.codecreater.util.dynamic." + className, codeBuffer.toString(),
				"dealOneStatement", new Object[0]);
	}
}
