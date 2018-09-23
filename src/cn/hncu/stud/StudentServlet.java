package cn.hncu.stud;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hncu.domain.Book;
import cn.hncu.domain.Student;
import cn.hncu.pub.baseServlet.BaseServlet;
import cn.hncu.pub.transaction.TransactionProxyUtil;
import cn.hncu.stud.services.IStudentService;
import cn.hncu.stud.services.StudentService;

/**
 * &emsp;&emsp;该类继承BaseServlet，并且不覆盖doPost()和doGet()方法。<br/>
 * &emsp;&emsp;通过 BaseServlet 中的 doPost()和doGet() + 类反射技术。<br/>
 * &emsp;&emsp;就可以根据前端请求的action所对应的参数确定当前StudentServlet应该处理的业务方法。
 * 
 * <br/><br/><b>CreateTime:</b><br/>&emsp;&emsp;&emsp;&ensp;2018年9月22日 下午11:42:09	
 * @author 宋进宇  Email:447441478@qq.com
 */
public class StudentServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
    
	//注入 service
	//IStudentService service = new StudentService();
	//通过 事务代理工具加工一下 普通的StudentService类的实例
	IStudentService service = TransactionProxyUtil.getTransactionProxy( new StudentService() );
	
	/**
	 * 添加学生信息
	 * @param request 请求对象
	 * @param response 响应对象
	 * @throws ServletException
	 * @throws IOException
	 */
	public void addInfo(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		//收集参数 studName,bookName,bookPrice
		Student student = new Student();
		//收集并封装学生信息
		String studName = request.getParameter("studName");
		//按理应该进行格式校验，这里略了。
		student.setName(studName);
		//收集并封装图书信息
		String[] bookNames = request.getParameterValues("bookName");
		String[] bookPrices = request.getParameterValues("bookPrice");
		if( bookNames != null ) {
			for (int i = 0; i < bookNames.length; i++) {
				if( bookNames[i] != null && bookNames[i].trim().length() > 0 ) {
					Book book = new Book();
					//封装图书名称
					book.setName( bookNames[i] );
					Double price = null;
					if( bookPrices != null && bookPrices.length > i ) {
						try {
							price = Double.valueOf( bookPrices[i] );
						} catch (NumberFormatException e) {
							throw new RuntimeException("图书"+(i+1)+"价格 格式非法："+bookPrices[i], e);
						}
					}
					//封装图书价格
					book.setPrice(price);
					//封装图书所属者
					book.setStudent(student);
					//封装学生所拥有的图书
					student.getBooks().add(book);
				}
			}
		}
		
		String msg = "";
		try {
			msg = service.addInfo(student);
		} catch (Exception e) {
			e.printStackTrace();
			msg="信息添加失败！！！";
		}
		response.getWriter().println(msg);
	}
	/**
	 * 默认业务：查询所有学生信息
	 * @param request 请求对象
	 * @param response 响应对象
	 * @throws ServletException
	 * @throws IOException
	 */
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//调用业务层
		List<Map<String, Object>> studs = null;
		try {
			studs = service.queryAll();
		} catch (SQLException e) {
			e.printStackTrace();
			studs = new ArrayList<Map<String, Object>>();
		}
		//把查询结果存储在request容器中
		request.setAttribute("studs", studs);
		//进行结果页面显示
		request.getRequestDispatcher("/jsps/show.jsp").forward(request, response);
	}

}
