package com.wanggt.freedom.codecreater.core.symbol;

import com.wanggt.freedom.codecreater.annotation.CodeCreaterIgnore;
import com.wanggt.freedom.codecreater.annotation.CodeCreaterType;
import com.wanggt.freedom.codecreater.core.Student;

@CodeCreaterType
public class CodeCreaterTestBean {

	@CodeCreaterIgnore
	private String author;

	private Student[] students;

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Student[] getStudents() {
		return students;
	}

	public void setStudents(Student[] students) {
		this.students = students;
	}

}
