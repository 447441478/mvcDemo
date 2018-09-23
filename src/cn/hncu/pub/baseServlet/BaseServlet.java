package cn.hncu.pub.baseServlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * &emsp;&emsp;为继承该类(Servlet)的子类(Servlet)进行动态调用所需业务。
 * 根据前端所带的 action 的参数的值加类反射技术。就可以决定该调用哪个方法处理业务。<br/>
 * &emsp;&emsp;<b>注意：</b>子类不能覆盖doGet()和doPost()方法，如果覆盖了那么继承该类就没有意义了。因为这里运用了多态的思想。
 * <br/><br/><b>CreateTime:</b><br/>&emsp;&emsp;&emsp;&ensp; 2018年9月22日 下午11:50:58	
 * @author 宋进宇  Email:447441478@qq.com
 */
public abstract class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * 当 action值为null或者为""的时候，默认的处理业务方法。
     * @param request 请求对象
     * @param response 响应对象
     * @throws ServletException 
     * @throws IOException
     */
	public abstract void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//能调用到这个方法是因为子类没有覆盖该方法，所以调用了父类(BaseServlet)的方法。
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		//当action为 null或者为""时执行默认的业务
		if( action == null || action.trim().length() == 0) {
			action="execute";
		}
		//注意 this 是BaseServlet的子类对象！！！！多态new谁调谁。
		Class<? extends BaseServlet> clazz = this.getClass();
		try {
			Method method = clazz.getMethod(action,HttpServletRequest.class,HttpServletResponse.class );
			//注意 this 是BaseServlet的子类对象！！！！多态new谁调谁。
			method.invoke(this, request,response);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage(),e);
		}
		
	}
}
