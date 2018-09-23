package cn.hncu.stud.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hncu.domain.Student;
import cn.hncu.pub.IDGenerator;
import cn.hncu.pub.MyConnPool;
import cn.hncu.stud.dao.IStudentDAO;

public class StudentDaoJdbc implements IStudentDAO {

	@Override
	public List<Map<String, Object>> queryAll() throws SQLException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		// 获取连接
		Connection con= MyConnPool.getConnection();
		// 获取状态集
		Statement st = con.createStatement();
		// 查询
		ResultSet resultSet = st.executeQuery(" select * from student ");
		while (resultSet.next()) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("id", resultSet.getString("id"));
			map.put("name", resultSet.getString("name"));
			list.add(map);
		}
		con.close();
		return list;
	}

	@Override
	public void save(Student student) throws SQLException{
		Connection con = MyConnPool.getConnection();
		//补一个学生id
		String uuid = IDGenerator.getUUID();
	
		String sql = "insert into student(id,name) values(?,?)";
		PreparedStatement pst = con.prepareStatement(sql);
		//补全sql
		pst.setString(1, uuid);
		pst.setString(2, student.getName());
		pst.executeUpdate();
		//能到这里说明插入数据库成功
		student.setId( uuid ); //补全student的id为存储books时提供外键
	}

}
