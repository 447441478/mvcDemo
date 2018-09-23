package cn.hncu.pub;

import java.util.UUID;

/**
 * 生成id的工具类
 * CreateTime: 2018年9月22日 上午10:01:40	
 * @author 宋进宇  Email:447441478@qq.com
 */
public class IDGenerator {
	//私有化构造函数
	private IDGenerator() {}
	/**
	 * 生成一个UUID
	 * @return 32位的UUID
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
