package cn.hncu.book.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import cn.hncu.book.dao.IBookDAO;
import cn.hncu.domain.Book;
import cn.hncu.pub.MyConnPool;

public class BookDaoJdbc implements IBookDAO {

	@Override
	public void saveBooks(List<Book> books) throws SQLException {
		Connection con = MyConnPool.getConnection();
		String sql = "insert into book(name,price,student_id) values(?,?,?)";
		PreparedStatement pst = con.prepareStatement(sql);
		int i = 0;
		for (Book book : books) {
			pst.setString(1, book.getName());
			pst.setDouble(2, book.getPrice());
			/* 如果能到这里说明前面存储student是成功的，
			 * 所以这里通过student引用可以获取到student_id
			 */
			pst.setString(3, book.getStudent().getId());
			//设置障碍为演示事务回滚
			if( i++ == 2) {
				pst.setInt(4, i);
			}
			//加入批量处理
			pst.addBatch();
		}
		//执行批量处理
		pst.executeBatch();
	}
	
}
