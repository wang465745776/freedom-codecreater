package com.wanggt.freedom.codecreater.util;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Test;

public class PathUtilTest {

	@Test
	public void test_getResource() throws IOException {
		Assert.assertEquals(11, PathUtil.getResource(getClass(), "PathUtilTestFile/getResource/test.txt").available());

		Assert.assertEquals(11, PathUtil.getResource(getClass(), "//PathUtilTestFile/getResource/test.txt").available());

		Assert.assertEquals(11, PathUtil.getResource(getClass(), "\\PathUtilTestFile/getResource/test.txt").available());

		Assert.assertEquals(11, PathUtil.getResource(getClass(), "\\///PathUtilTestFile/getResource/test.txt")
				.available());
	}

	@Test
	public void test_getResourcePath() throws IOException, URISyntaxException {
		Assert.assertEquals(
				"D:\\workspace4own\\GAMSWS\\gamsws\\WEB-INF\\test-classes\\com\\wpams\\gamsws\\core\\util\\PathUtilTestFile\\getResource\\test.txt",
				PathUtil.getResourcePath(getClass(), "PathUtilTestFile/getResource/test.txt"));
	}

	@Test
	public void test_getProjectRootPath() throws IOException, URISyntaxException {
		System.out.println(PathUtil.getProjectRootPath());
	}
	
	@Test
	public void test_getClassesRootPath() throws IOException, URISyntaxException {
		System.out.println(PathUtil.getClassesRootPath());
	}
}
