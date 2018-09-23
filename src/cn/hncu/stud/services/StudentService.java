package cn.hncu.stud.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import cn.hncu.book.dao.IBookDAO;
import cn.hncu.book.dao.mysql.BookDaoJdbc;
import cn.hncu.domain.Student;
import cn.hncu.stud.dao.IStudentDAO;
import cn.hncu.stud.dao.mysql.StudentDaoJdbc;

public class StudentService implements IStudentService {
	//注入 studDao
	IStudentDAO studDao = new StudentDaoJdbc();
	//注入 bookDao
	IBookDAO bookDao = new BookDaoJdbc();
	
	@Override
	public List<Map<String, Object>> queryAll() throws SQLException {
		return studDao.queryAll();
	}
	
	
	@Override
	public String addInfo(Student student)throws SQLException {
		//先存储一方
		studDao.save(student); 
		//再存储多方
		bookDao.saveBooks( student.getBooks() );
		return "信息添加成功！！！";
	}

}
