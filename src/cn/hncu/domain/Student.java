package cn.hncu.domain;

import java.util.ArrayList;
import java.util.List;

public class Student {
	//学生标识：主键,UUID
	private String id;
	//学生姓名
	private String name;
	
	//student与book是一对多关系，所有应该有个集合存放book
	//声明并创建一个List<Book>集合
	private List<Book> books = new ArrayList<Book>(); //最好初始化一个容器
	//最好显示创建一个空参构造函数
	public Student() {
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
}
