package cn.hncu.pub;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Queue;


/**
 * 我的数据库连接池：
 * 动态代理+ThreadLocal
 * 做成一个线程共享一个连接
 * CreateTime: 2018年9月21日 下午11:36:49	
 * @author 宋进宇  Email:447441478@qq.com
 */
public class MyConnPool {
	//存放连接的队列
	private static Queue<Connection> conPool = new LinkedList<Connection>();
	/* 存放 Thread 和 con 的键值对，Thread为当前线程，隐示状态。
	 * tlPool.get/set方法内部会通过Thread.currentThread()获取到当前线程。
	 */
	private static ThreadLocal<Connection> tlPool = new ThreadLocal<Connection>();
	//连接池默认大小为3
	private static int size = 3;
	static {
		Properties p = new Properties();
		try {
			//加载配置文件
			p.load( MyConnPool.class.getClassLoader().getResourceAsStream("myConnPool.properties"));
			//读取配置信息
			String driver = p.getProperty("driver");
			String url = p.getProperty("url");
			String username = p.getProperty("username");
			String password = p.getProperty("password");
			String strSize = p.getProperty("size");
			size = Integer.valueOf( strSize );
			//加载驱动
			Class.forName( driver );
			for( int i = 0; i < size; i++ ) {
				//因为匿名内部类需要调用该对象，所以用final修饰
				final Connection con = DriverManager.getConnection(url, username, password);
				//关键点
				//这是一个 Connection 接口的代理对象
				Object proxiedObj = Proxy.newProxyInstance(
										MyConnPool.class.getClassLoader(), 
										new Class[] {Connection.class},
										new InvocationHandler() { //这个才是关键点
											//参数 proxy对象 就是proxiedObj对象, method就是被调用的方法对象 , args方法参数
											@Override
											public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
												//判断被调用的方法是否是close()方法
												if( "close".equals( method.getName() ) ) {
													//移除当前线程所对应的连接对象。
													tlPool.set(null);
													//把连接对象返回连接池中
													conPool.add( (Connection) proxy );
													System.out.println("还回来一个conn...");
													return null;
												}
												return method.invoke(con, args);
											}
										});
				Connection con2 = (Connection) proxiedObj;
				conPool.add(con2);
			}
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	/**
	 * 获取数据库连接对象
	 * @return 数据库连接对象
	 */
	public synchronized static Connection getConnection() {
		//先通过tlPool获取con
		Connection con = tlPool.get();
		//判断con是否为空
		if( con == null ) {//能进来说明当前线程是没有获得到con的，从连接池中获取一个连接
			
			//判断连接池是否为空
			if( conPool.size() <= 0) {
				//能进来说明连接池为空，当前线程睡一下再重新调用getConnection()方法
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return getConnection();
			}
			con = conPool.poll();
			/* 能到这里说明当前线程获取到了一个连接对象，
			 * 把连接对象存储到tlPool中，这样就形成一个线程对应一个连接对象了。
			 */
			tlPool.set(con);
		}
		return con;
	}
}
