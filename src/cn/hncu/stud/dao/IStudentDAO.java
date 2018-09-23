package cn.hncu.stud.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import cn.hncu.domain.Student;

public interface IStudentDAO {
	/**
	 * 查询所有学生信息
	 * @return 所有学生信息的集合
	 */
	List< Map<String, Object> > queryAll() throws SQLException;

	void save(Student student)  throws SQLException;
}
