package cn.hncu.pub.transaction;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

import cn.hncu.pub.MyConnPool;
/**
 * 生成一个代理对象，该代理对象的执行方法时，
 * 根据注解@Transaction的有无，来判断是否自动处理事务。
 * CreateTime: 2018年9月22日 下午10:50:39	
 * @author 宋进宇  Email:447441478@qq.com
 */
public class TransactionProxyUtil implements InvocationHandler{
	//被代理的原对象
	private Object srcObj;
	
	//构造方法保护化
	protected TransactionProxyUtil(Object srcObj) {
		this.srcObj = srcObj;
	}
	/**
	 * 生成一个代理对象，该代理对象的执行方法时，
	 * 根据注解@Transaction的有无，来判断是否自动处理事务。<br/>
	 * 注意：使用注解时，需要在 srcObj的接口中注解相应的方法。
	 * @param srcObj 被代理的原对象
	 * @return 代理对象
	 */
	@SuppressWarnings("unchecked")
	public static<T> T getTransactionProxy(T srcObj) {
		Object proxiedObj = Proxy.newProxyInstance(
								//以当前类的加载器作为生成代理对象的类加载器
								TransactionProxyUtil.class.getClassLoader(),
								//生成的代理对象需要实现的接口
								srcObj.getClass().getInterfaces(),
								//InvocationHandler的一个实现类
								new TransactionProxyUtil(srcObj) );
		
		return (T) proxiedObj;
	}
	
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		//判断method是否被 @Transaction注解
		if( method.isAnnotationPresent( Transaction.class ) ) {
			System.out.println(method.getName()+"方法被@Transaction 注解，开始事务处理");
			//能进行说明该方法需要进行事务处理
			Connection con = null;
			Object returnValue = null;
			try {
				con = MyConnPool.getConnection();
				con.setAutoCommit(false); //开启事务
				
				/////////////执行原对象的业务方法//////////////
				returnValue = method.invoke(srcObj, args);
				//////////////////////////////////////////
				
				//能到这里说明业务处理正常，把事务提交
				con.commit();
			} catch (Exception e) {
				//e.printStackTrace();
				//能进入到这里说明业务处理出现问题，把事务进行回滚
				con.rollback();
				//把捕捉到的异常抛出
				throw e;
			} finally {
				
				if( con != null ) {
					//还原事务自动提交
					con.setAutoCommit(true);
					// 把连接关闭，其实是换回连接池
					con.close();
				}
			}
			return returnValue;
		}
		System.out.println(method.getName()+"方法没有被@Transaction 注解");
		//能到这里 说明是method没有被 @Transaction 注解，则该方法原样执行
		return method.invoke(srcObj, args);
	}
	
	
	
	
}
