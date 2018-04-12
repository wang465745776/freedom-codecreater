package com.wanggt.freedom.codecreater.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wanggt.freedom.codecreater.core.observer.HasOperate;
import com.wanggt.freedom.codecreater.core.observer.HasParamParser;
import com.wanggt.freedom.codecreater.core.observer.HasSymbol;
import com.wanggt.freedom.codecreater.core.operate.DeleteLastCharOperate;
import com.wanggt.freedom.codecreater.core.operate.FirstCharLowerCaseOperate;
import com.wanggt.freedom.codecreater.core.operate.FirstCharOperate;
import com.wanggt.freedom.codecreater.core.operate.FirstCharUpperCaseOperate;
import com.wanggt.freedom.codecreater.core.operate.LowerCaseOperate;
import com.wanggt.freedom.codecreater.core.operate.Operate;
import com.wanggt.freedom.codecreater.core.operate.RemoveUnderlineOperate;
import com.wanggt.freedom.codecreater.core.operate.UpperCaseOperate;
import com.wanggt.freedom.codecreater.core.output.ConsoleOutput;
import com.wanggt.freedom.codecreater.core.output.Output;
import com.wanggt.freedom.codecreater.core.parser.TemplateParser;
import com.wanggt.freedom.codecreater.core.symbol.BranchSymbol;
import com.wanggt.freedom.codecreater.core.symbol.LoopSymbol;
import com.wanggt.freedom.codecreater.core.symbol.ReplaceSymbol;
import com.wanggt.freedom.codecreater.core.symbol.Symbol;
import com.wanggt.freedom.codecreater.param.ParamParser;
import com.wanggt.freedom.codecreater.util.IOUtil;
import com.wanggt.freedom.codecreater.util.ProjectConfig;

/**
 * 代码生成器。<br>
 * 
 * 此类为此代码生成器的核心，是使用这个代码生成器所重要的类。
 * @author freedom wang
 * @date 2017年6月21日上午9:22:59
 * @since 1.0
 */
public class CodeCreater {
	/** 日志对象 */
	private static Logger logger = LoggerFactory.getLogger(CodeCreater.class);

	/** 注册操作 */
	private List<Operate> operates;

	/** 全局操作中心 */
	private GlobalOperateCenter globalOperateCenter;

	/** 标记 */
	private List<Symbol> symbols;

	/** 输出方式类 */
	private List<Output> outputs;

	/** 参数解析器 */
	private ParamParser paramParser;

	/**
	 * 创建一个代码生成器，并进行初始化
	 * @author freedom wang
	 * @date 2017年6月23日上午10:17:28
	 * @since 1.0
	 */
	public CodeCreater() {
		init();
	}

	/**
	 * 调用此方法对代码生成器进行初始化
	 * @author freedom wang
	 * @date 2017年6月23日上午9:10:02
	 * @since 1.0
	 */
	private void init() {
		// 默认注册一些操作对象
		operates = new ArrayList<Operate>();
		operates.add(new UpperCaseOperate());
		operates.add(new LowerCaseOperate());
		operates.add(new FirstCharLowerCaseOperate());
		operates.add(new FirstCharUpperCaseOperate());
		operates.add(new DeleteLastCharOperate());
		operates.add(new FirstCharOperate());
		operates.add(new RemoveUnderlineOperate());

		// 默认注册分支、循环、替换三种标记
		symbols = new ArrayList<Symbol>();
		symbols.add(new BranchSymbol());
		symbols.add(new LoopSymbol());
		symbols.add(new ReplaceSymbol());

		// 默认注册输出方式
		outputs = new ArrayList<Output>();
		outputs.add(new ConsoleOutput());// 控制台输出

		// 定义一个全局操作中心
		globalOperateCenter = new GlobalOperateCenter();

		// 定义参数解析器
		paramParser = new ParamParser();
		paramParser.setGlobalOperateCenter(globalOperateCenter);

		// 使用观察者模式，给标记对象设置标记对象所需要的一些数据
		for (Symbol oneSymbol : symbols) {
			if (oneSymbol instanceof HasParamParser) {
				((HasParamParser) oneSymbol).setParamParser(paramParser);
			}

			if (oneSymbol instanceof HasSymbol) {
				((HasSymbol) oneSymbol).setSymbols(symbols);
			}

			if (oneSymbol instanceof HasOperate) {
				((HasOperate) oneSymbol).setOperates(operates);
			}
		}
	}

	/**
	 * 调用此方法生成代码
	 * @param templateStream 模板输入流
	 * @param properties 属性对象
	 * @throws IOException
	 * @author freedom wang
	 * @date 2017年6月22日下午6:33:59
	 * @since 1.0
	 */
	public void createCode(InputStream templateStream, Properties[] properties) throws IOException {
		// 解析属性
		paramParser.setProperties(properties);

		// 从输入流中获取模板代码
		String templateCode = IOUtil.getString(templateStream);

		// 解析模板代码
		String code = TemplateParser.analyzeTemplate(templateCode, symbols);

		for (Output oneOutput : outputs) {
			oneOutput.output(code);
		}
	}

	/**
	 * 调用此方法生成代码
	 * @param templateStream
	 * @param params
	 * @throws IOException
	 * @author freedom wang
	 * @date 2018年4月12日下午11:09:06
	 * @version 1.0
	 */
	public void createCode(InputStream templateStream, Object[] params) throws IOException {
		// 传入的可以是Properties对象，可以是JavaBean，JavaBean必须要使用注解标注
		paramParser.setParams(params);

		// 获取模块代码
		String templateCode = IOUtil.getString(templateStream);

		// 解析模板代码
		String code = TemplateParser.analyzeTemplate(templateCode, symbols);

		for (Output oneOutput : outputs) {
			oneOutput.output(code);
		}
	}

	/**
	 * 调用此方法生成代码
	 * @param templateStream
	 * @param properties
	 * @param params
	 * @throws IOException
	 * @author freedom wang
	 * @date 2018年4月12日下午11:09:13
	 * @version 1.0
	 */
	public void createCode(InputStream templateStream, Properties[] properties, Object[] params) throws IOException {
		// 传入的可以是Properties对象，可以是JavaBean，JavaBean必须要使用注解标注
		paramParser.setProperties(properties);
		paramParser.setParams(params);

		// 获取模块代码
		String templateCode = IOUtil.getString(templateStream);

		// 解析模板代码
		String code = TemplateParser.analyzeTemplate(templateCode, symbols);

		for (Output oneOutput : outputs) {
			oneOutput.output(code);
		}
	}

	/**
	 * 注册自定义操作
	 * @param operate
	 * @author freedom wang
	 * @date 2017年6月21日下午6:26:02
	 * @since 1.0
	 */
	public void addOperate(Operate operate) {
		boolean exist = false;

		// 判断是否已存在相同的标记，如果已存在，则不再添加
		for (Operate oneOperate : operates) {
			if (oneOperate.getMark().equals(operate.getMark())) {
				logger.warn("添加自定义操作失败，原因是标记已存在，标记为：{}", oneOperate.getMark());
				exist = true;
			}
		}

		if (!exist) {
			operates.add(operate);
		}
	}

	/**
	 * 获取注册的操作
	 * @return
	 * @author freedom wang
	 * @date 2017年6月21日下午9:46:20
	 * @since 1.0
	 */
	public List<Operate> getOperates() {
		return operates;
	}

	/**
	 * 获取已注册的模板标记
	 * @return
	 * @author freedom wang
	 * @date 2017年6月23日上午10:19:43
	 * @since 1.0
	 */
	public List<Symbol> getSymbols() {
		return symbols;
	}

	/**
	 * 注册自定义模板标记
	 * @param symbol
	 * @author freedom wang
	 * @date 2017年6月23日上午10:19:09
	 * @since 1.0
	 */
	public void setSymbols(Symbol symbol) {
		boolean exist = false;

		// 判断是否已存在相同的标记，如果已存在，则不再添加
		for (Symbol oneSymbol : symbols) {
			if (oneSymbol.getSymbolStart().equals(symbol.getSymbolStart())) {
				logger.warn("添加自定义标记失败，原因是标记已存在，开始标记为：{}", oneSymbol.getSymbolStart());
				exist = true;
			}
		}

		if (!exist) {
			symbols.add(symbol);
		}
	}

	/**
	 * 获取代码生成器中所有已注册的输出方式
	 * @return
	 * @author freedom wang
	 * @date 2017年6月25日上午11:43:02
	 * @since 1.0
	 */
	public List<Output> getOutputs() {
		return outputs;
	}

	/**
	 * 注册一个自定义的输出方式
	 * @param outputs
	 * @author freedom wang
	 * @date 2017年6月25日上午11:42:10
	 * @since 1.0
	 */
	public void addOutput(Output output) {
		outputs.add(output);
	}

	/**
	 * 设置自定义的代码生成配置，此配置会覆盖默认的配置
	 * @param properties
	 * @author freedom wang
	 * @date 2017年6月25日上午11:37:52
	 * @since 1.0
	 */
	public void setCustomProjectProperties(Properties properties) {
		ProjectConfig.setCustomProperties(properties);
	}

	/**
	 * 注册自定义全局操作，全局操作会对该生成器下的某一个参数都进行操作
	 * @param param
	 * @param operate
	 * @author freedom wang
	 * @date 2017年7月24日下午2:07:24
	 * @since 1.0
	 */
	public void addGlobalOpetate(String param, Operate operate) {
		globalOperateCenter.addGlobalOperate(param, operate);
	}

	/**
	 * 返回某参数的全局操作对象
	 * @return
	 * @author freedom wang
	 * @date 2017年7月24日下午2:39:37
	 * @since 1.0
	 */
	public List<Operate> getGlobalOperate(String param) {
		return globalOperateCenter.getGlobalOperate(param);
	}
}
