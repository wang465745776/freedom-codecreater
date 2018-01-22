# freedom-codecreater 代码生成器

## 使用方式
1. 简单使用
```
// 获取DAO代码模板
InputStream template = MainCodeCreater.class.getResourceAsStream("/template/Daotemplate.template");

// 创建一个代码生成器
CodeCreater creater= new CodeCreater();

// 设置输出方式，代码输出到一个文件。默认代码输出到控制台
creater.addOutput(new FileOutput(basePath + dealTableName + "Dao.java"));

// 设置模板和参数，开始生成代码
creater.createCode(template, new Properties[] { baseProperties });
```

## 模板语法
示例如下，这个一个生成POJO的模板
```
/**
 * ${moduleNameC}-bean
 * @author ${author}
 * @date ${date}
 * @version ${version}
 */
public class ${<fu>tableName}Bean %{*(@java@${pager})extends PageBase }%{
#{*(columns)	private ?{<javatype>columnType}? ?{<fl>columnName}?;// ?{columnDescription}?
}#
#{*(columns)	public ?{<javatype>columnType}? get?{<fu>columnName}?() {
		return ?{<fl>columnName}?;
	}
	
	public void set?{<fu>columnName}?(?{<javatype>columnType}? ?{<fl>columnName}?) {
		this.?{<fl>columnName}? = ?{<fl>columnName}?;
	}
	
}#}
```
### 数据绑定
绑定语句的开始：${
绑定语句的结束：}
语句操作的开始：<
语句操作的结束：>
使用动态java解析的标记：@java@

### 判断模板
判断语句的开始：%{
判断语句的结束：}%
判断参数的开始：=*(
判断参数的结束：=)
语句操作的开始：=<
语句操作的结束：=>
判断参数使用动态java解析的标记：@java@

### 循环语句
循环语句开始：#{
循环语句结束：}#
循环参数的开始：*(
循环参数的结束)
循环操作的开始：@(
循环操作的结束：)
循环语句内数据绑定的开始：?{
循环语句内数据绑定的结束：}?
循环语句内数据绑定的操作的开始：<
循环语句内数据绑定的操作的结束：>

## 默认执行的操作
- `u`
全部大写
- `l`
全部小写
- `fu`
首字母大写
- `fl`
首字母小写
- `dl`
删除最后一个字符
- `f`
只取第一个字符
- `r_`
去除下划线

## 更新日志
- 2017-07-24 
新增全局操作功能
- 2018-01-22
删除一些无用代码

## 需求
- 2017-07-25
内部参数，在模板内也可以定义参数
- 2017-07-25
循环内的判断
- 2017-07-28
想要重构各个符号，变量的获取应该基于链式的
- 2017-08-26
对于判断的使用太麻烦，能不能加入简单地指令进行判断
- 2018-1-22
循环语句应支持自定义项item和序号的名称index,可以使用语法类似如下：`(item,index) in items`
