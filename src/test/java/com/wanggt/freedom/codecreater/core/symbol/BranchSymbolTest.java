package com.wanggt.freedom.codecreater.core.symbol;

import org.junit.Assert;
import org.junit.Test;

public class BranchSymbolTest {

	@Test
	public void testGetSymbolStart() {
		BranchSymbol branchSymbol = new BranchSymbol();
		Assert.assertEquals("%{", branchSymbol.getSymbolStart());
	}

	@Test
	public void testGetSymbolEnd() {
		BranchSymbol branchSymbol = new BranchSymbol();
		Assert.assertEquals("}%", branchSymbol.getSymbolEnd());
	}
//
//	@Test
//	public void testParse() throws IOException {
//		InputStream resourceAsStream = BranchSymbolTest.class.getResourceAsStream("/BranchSymbol.template");
//		InputStream propertiesStream = BranchSymbolTest.class.getResourceAsStream("/BranchSymbol.properties");
//		
//		Properties properties = new Properties();
//		properties.load(propertiesStream);
//		
//		List<Operate> operates = new ArrayList<Operate>();
//		operates.add(new UpperCaseOperate());
//		operates.add(new LowerCaseOperate());
//		operates.add(new FirstCharLowerCaseOperate());
//		operates.add(new FirstCharUpperCaseOperate());
//		operates.add(new DeleteLastCharOperate());
//		operates.add(new FirstCharOperate());
//		
//		BranchSymbolBean bean = new BranchSymbolBean();
//		bean.setHaha(true);
//		
//		ParamParser paramParser = new ParamParser(new Object[]{bean});
//
//		String code = IOUtil.getString(resourceAsStream);
//
//		BranchSymbol branchSymbol = new BranchSymbol();
//		branchSymbol.setOperates(operates);
//		branchSymbol.setParamParser(paramParser);
//		
//		String parse = branchSymbol.parse(code);
//		System.out.println(parse);
//	}
}
