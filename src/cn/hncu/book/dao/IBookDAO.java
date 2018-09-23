package cn.hncu.book.dao;

import java.sql.SQLException;
import java.util.List;

import cn.hncu.domain.Book;

public interface IBookDAO {
	/**
	 * 添加一些图书
	 * @param books 图书的集合
	 * @throws SQLException
	 */
	void saveBooks(List<Book> books) throws SQLException;
	
}
