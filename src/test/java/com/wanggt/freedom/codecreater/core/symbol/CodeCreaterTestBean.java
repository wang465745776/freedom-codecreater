package com.wanggt.freedom.codecreater.core.symbol;

import com.wanggt.freedom.codecreater.annotation.CodeCreaterIgnore;
import com.wanggt.freedom.codecreater.annotation.CodeCreaterType;

@CodeCreaterType
public class CodeCreaterTestBean {

	@CodeCreaterIgnore
	private String author;

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

}