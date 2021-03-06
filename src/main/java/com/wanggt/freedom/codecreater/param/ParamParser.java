package com.wanggt.freedom.codecreater.param;

import java.lang.reflect.Field;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wanggt.freedom.codecreater.annotation.CodeCreaterIgnore;
import com.wanggt.freedom.codecreater.annotation.CodeCreaterParam;
import com.wanggt.freedom.codecreater.annotation.CodeCreaterType;
import com.wanggt.freedom.codecreater.core.GlobalOperateCenter;
import com.wanggt.freedom.codecreater.core.ValueOperate;
import com.wanggt.freedom.codecreater.core.observer.HasGlobalOperateCenter;
import com.wanggt.freedom.codecreater.core.operate.Operate;
import com.wanggt.freedom.codecreater.core.parser.TemplateBean;
import com.wanggt.freedom.codecreater.exception.ErrorFormatException;

/**
 * 参数解析器
 * @author freedom wang
 * @date 2017年6月23日上午9:14:04
 * @since 1.0
 */
public class ParamParser implements HasGlobalOperateCenter {
	/** 日志对象 */
	private static Logger logger = LoggerFactory.getLogger(ParamParser.class);

	/** 属性 */
	private Properties[] properties = null;

	/** 参数 */
	private Object[] params = null;

	/** 全局操作中心 */
	private GlobalOperateCenter globalOperateCenter;

	public ParamParser() {
		super();
	}

	public ParamParser(Object[] params) {
		super();
		this.params = params;
	}

	public ParamParser(Properties[] properties) {
		super();
		this.properties = properties;
	}

	public ParamParser(Properties[] properties, Object[] params) {
		super();
		this.properties = properties;
		this.params = params;
	}

	public Properties[] getProperties() {
		return properties;
	}

	public void setProperties(Properties[] properties) {
		this.properties = properties;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}

	/**
	 * 获取代码生成的参数值
	 * @param key 属性名或者类字段名
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @author freedom wang
	 * @date 2017年6月23日上午9:20:09
	 * @since 1.0
	 */
	public Object getParam(String key, TemplateBean templateBean)
			throws IllegalArgumentException, IllegalAccessException {
		Object value = null;

		// 校验key的格式是否正确
		if (key == null || "".equals(key)) {
			throw new NullPointerException("变量名称不能为空！");
		}

		if (!key.matches("^[^\\.]+(\\.[^\\.]+)*$")) {
			throw new ErrorFormatException("变量名格式错误,错误变量名为：" + key);
		}

		String[] multiKey = key.split("\\.");

		// 获取根变量的值,若根变量的值为null，则直接返回null
		value = getValue(multiKey[0], templateBean);
		if (value == null) {
			return null;
		}

		for (int i = 1; i < multiKey.length; i++) {
			value = getFieldValue(value, multiKey[i]);
		}

		// 如果全局操作对象不为空，则进行全局操作
		if (globalOperateCenter != null && value instanceof String) {
			// 获取该对象的全局操作对象
			List<Operate> globalOperate = globalOperateCenter.getGlobalOperate(key);
			if (globalOperate != null && globalOperate.size() > 0) {
				StringBuilder operate = new StringBuilder();
				for (Operate oneOperate : globalOperate) {
					operate.append(oneOperate.getMark()).append(",");
				}
				operate.deleteCharAt(operate.length() - 1);

				value = ValueOperate.dealValue(operate.toString(), String.valueOf(value), globalOperate);
			}
		}

		return value;
	}

	/**
	 * 获取变量的值
	 * @param key
	 * @param templateBean
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @author freedom wang
	 * @date 2018年4月13日下午4:45:42
	 * @since 3.0.0
	 */
	private Object getValue(String key, TemplateBean templateBean)
			throws IllegalArgumentException, IllegalAccessException {
		Object value = null;

		// 查询模板对象链中的局部变量，若查找到变量值，则返回。这表明局部变量权重最高
		TemplateBean linkBean = templateBean;
		do {
			Hashtable<Object, Object> localProperties = linkBean.getLocalProperties();
			value = localProperties.get(key);
			linkBean = linkBean.getPreTemplate();
		} while (value == null && linkBean != null);

		if (value != null) {
			return value;
		}

		// 参数不为空,获取参数中的信息
		if (params != null && params.length > 0) {
			for (Object oneParam : params) {
				// 判断是否存在@CodeCreaterType注解
				CodeCreaterType codeCreaterType = oneParam.getClass().getAnnotation(CodeCreaterType.class);
				if (codeCreaterType != null) {
					// 取得对象的所有字段，判断每个字段是否有@CodeCreaterIgnore注解，有此注解表示忽略
					Field[] allFields = oneParam.getClass().getDeclaredFields();

					for (Field oneField : allFields) {
						if (oneField.getAnnotation(CodeCreaterIgnore.class) == null) {
							CodeCreaterParam codeCreaterParam = oneField.getAnnotation(CodeCreaterParam.class);

							if (codeCreaterParam == null) {
								if (oneField.getName().equals(key)) {
									oneField.setAccessible(true);
									value = oneField.get(oneParam);
									break;
								}
							} else {
								String newKey = codeCreaterParam.value();
								if ((newKey.equals("") && oneField.getName().equals(key)) || newKey.equals(key)) {
									oneField.setAccessible(true);
									value = oneField.get(oneParam);
									break;
								}
							}
						}
					}
				} else {
					// 若类上不存在@CodeCreaterType注解，则判断属性上是否存在@CodeCreaterParam的注解
					Field[] allFields = oneParam.getClass().getDeclaredFields();

					// 循环每一个声明的字段，判断是否存在@CodeCreaterType注解
					for (Field oneField : allFields) {
						CodeCreaterParam codeCreaterParam = oneField.getAnnotation(CodeCreaterParam.class);

						// 若存在@CodeCreaterType注解，则表明此字段作为代码生成参数，跟需要查询的参数名进行匹配
						if (codeCreaterParam != null) {
							String newKey = codeCreaterParam.value();
							if ((newKey.equals("") && oneField.getName().equals(key)) || newKey.equals(key)) {
								oneField.setAccessible(true);
								value = oneField.get(oneParam);
								break;
							}
						}
					}
				}
			}
		}
		
		if (value != null) {
			return value;
		}

		// 属性配置不为空，获取配置中的信息
		if (properties != null) {
			for (Properties oneProperties : properties) {
				if (oneProperties != null) {
					value = oneProperties.getProperty(key);

					if (value != null) {
						return value;
					}
				}
			}
		}

		return value;
	}

	/**
	 * 通过反射获取对象属性值，若不存在该字段，则返回null
	 * @param object 对象
	 * @param fieldName 字段名称
	 * @return
	 * @author freedom wang
	 * @date 2017年6月22日下午5:16:02
	 * @since 1.0
	 */
	public static Object getFieldValue(Object object, String fieldName) {
		Object value = null;

		// 取得对象的所有字段
		Field[] declaredFields = object.getClass().getDeclaredFields();

		// 循环每一个对象，判断字段名称
		for (Field oneField : declaredFields) {
			if (oneField.getName().equals(fieldName)) {
				oneField.setAccessible(true);
				try {
					value = oneField.get(object);
					break;
				} catch (IllegalArgumentException | IllegalAccessException e) {
					logger.error("获取对象属性值失败,对象为:{}，要获取的属性名为:{}", object, fieldName);
				}
			}
		}

		return value;
	}

	@Override
	public void setGlobalOperateCenter(GlobalOperateCenter globalOperateCenter) {
		this.globalOperateCenter = globalOperateCenter;
	}
}
