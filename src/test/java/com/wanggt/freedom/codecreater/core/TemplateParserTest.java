package com.wanggt.freedom.codecreater.core;

import org.junit.Assert;
import org.junit.Test;

public class TemplateParserTest {

	@Test
	public void testAnalyzeTemplate() {
	}

	@Test
	public void testGetFirstTemplateSymbol() {
	}

	@Test
	public void testHasTemplateCodeStringStringString() {
		String start = "%{";
		String end = "}%";
		String code = "%{*(aaa)%{}%";
		Assert.assertFalse(TemplateParser.hasTemplateCode(code, start, end));
	}

	@Test
	public void testHasTemplateCodeListOfSymbolString() {
	}

	@Test
	public void testGetFirstTemplateBeanStringIntStringString() {
		String start = "%{";
		String end = "}%";
		String code = "%{*(aaa)%{}%aaa}%";
		TemplateBean firstTemplateBean = TemplateParser.getFirstTemplateBean(code, 0, start, end);
		Assert.assertEquals("*(aaa)%{}%aaa", firstTemplateBean.getCode());
	}

	@Test
	public void testGetFirstTemplateBeanStringIntSymbol() {
	}

	@Test
	public void testGetFirstIndexStringIntStringString() {
		String start = "%{";	
		String end = "}%";
		String code = "%{*(aaa)%{}%aaa}%";
		Assert.assertEquals(0, TemplateParser.getFirstIndex(code, 0, start, end));
		Assert.assertEquals(-1, TemplateParser.getFirstIndex("%{*(aaa)%{}%aaa", 0, start, end));
	}

	@Test
	public void testGetFirstIndexStringIntSymbol() {
	}

	@Test
	public void testGetSymbolContentStart() {
	}

	@Test
	public void testRemoveSymbolStart() {
	}

	@Test
	public void testGetEndSymbolIndex() {
		String start = "%{";
		String end = "}%";
		String code = "%{*(aaa)%{}%aaa}%";
		Assert.assertEquals(15, TemplateParser.getEndSymbolIndex(code, 2, start, end));
	}

	@Test
	public void testCompareSymbolIndex() {
	}

	@Test
	public void testGetCompareSymbolIndex() {
	}

}
