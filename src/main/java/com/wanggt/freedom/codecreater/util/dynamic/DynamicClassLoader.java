package com.wanggt.freedom.codecreater.util.dynamic;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import com.wanggt.freedom.codecreater.util.PathUtil;

/**
 * 动态类加载器
 * @author freedom wang
 * @date 2017年6月21日下午3:32:28
 * @since 1.0
 */
public class DynamicClassLoader {

	/**
	 * 加载动态类
	 * @param className 类名
	 * @param code 类代码
	 * @return
	 * @throws ClassNotFoundException
	 * @author freedom wang
	 * @date 2017年6月21日下午3:32:39
	 * @since 1.0
	 */
	public static Class<?> findClass(String className, String code) throws ClassNotFoundException {
		// 获取一个Java编译器
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

		// Java标准文件管理器
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);

		// 设置编译参数， 指定放置生成的类文件的位置
		String classPath = PathUtil.getClassesRootPath();
		File classPathFile = new File(classPath);
		if (!classPathFile.exists()) {
			classPathFile.mkdirs();
		}

		List<String> options = new ArrayList<String>();
		options.add("-d");
		options.add(classPath);

		// 内存中的源代码保存在一个从JavaFileObject继承的类中
		JavaFileObject file = new StringJavaObject(className, code);
		List<JavaFileObject> compilationUnits = Arrays.asList(file);

		// 建立一个编译任务
		JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, options, null, compilationUnits);

		// 编译源程序
		if (task.call()) {
			return Class.forName(className);
		} else {
			return null;
		}
	}

	/**
	 * 获取类文件的路径
	 * @param className
	 * @return
	 * @author freedom wang
	 * @date 2017年6月21日下午3:48:20
	 * @since 1.0
	 */
	private static String getClassPath(String className) {
		String classPath = PathUtil.getClassesRootPath();
		if (!classPath.endsWith(File.separator)) {
			classPath = classPath + File.separator;
		}

		String packageName = className.substring(0, className.lastIndexOf("."));
		packageName = packageName.replace(".", File.separator);

		return classPath + packageName;
	}

	/**
	 * 删除类文件
	 * @param className
	 * @author freedom wang
	 * @date 2017年6月21日下午3:42:41
	 * @since 1.0
	 */
	public static void deleteClass(String className) {
		// 获取类文件的全限定名
		String classPath = getClassPath(className);
		classPath = classPath + File.separator + className.substring(className.lastIndexOf(".") + 1) + ".class";

		File file = new File(classPath);
		if (file.exists()) {
			file.delete();
		}
	}
}
