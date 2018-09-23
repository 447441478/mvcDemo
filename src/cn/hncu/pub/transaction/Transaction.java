package cn.hncu.pub.transaction;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 通过该注解能够识别调用的方法是否需要做事务
 * CreateTime: 2018年9月22日 下午10:44:55	
 * @author 宋进宇  Email:447441478@qq.com
 */
@Retention( RetentionPolicy.RUNTIME	) //让Transaction注解能够带到JVM中
@Target( value= {ElementType.METHOD} ) //规定Transaction注解只能在方法上使用
public @interface Transaction {

}
