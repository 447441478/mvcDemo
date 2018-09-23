package cn.hncu.stud.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import cn.hncu.domain.Student;
import cn.hncu.pub.transaction.Transaction;

public interface IStudentService {
	/**
	 * 查询所有学生信息
	 * @return 所有学生信息的集合
	 */
	List< Map<String, Object> > queryAll()throws SQLException;
	/**
	 * 添加学生和图书信息
	 * @param student
	 * @return
	 */
	@Transaction
	String addInfo(Student student)throws SQLException;

}
