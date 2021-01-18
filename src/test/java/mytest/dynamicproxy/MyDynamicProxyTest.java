package mytest.dynamicproxy;

import org.junit.Test;
import sun.misc.ProxyGenerator;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Proxy;
import java.nio.file.Files;

/**
 * @BelongsProject: spring
 * @BelongsPackage: mytest.dynamicproxy
 * @Author: Lovezly
 * @CreateTime: 2020-07-25 07:44
 * @Description: 动态代理测试类
 */
public class MyDynamicProxyTest {

	@Test
	public void testJdkDynamicProxy() {
		TargetInterface target = new TargetClass();
		TargetInterface targetClass = (TargetInterface) Proxy.newProxyInstance(TargetClass.class.getClassLoader(),
				TargetClass.class.getInterfaces(),
				(proxy, method, args) -> {
					System.out.println("before");
					method.invoke(target, args);
					System.out.println("after");
					return target;
				});
		targetClass.print("aaa");
		targetClass.println("aaa");

		// 使用ProxyGenerator类来查看被代理的文件
		createProxyClass();
	}

	public static void createProxyClass() {
		byte[] bytes = ProxyGenerator.generateProxyClass("TargetInterface", new Class[]{TargetInterface.class});
		try {
			Files.write(new File("E:\\MyData\\MyInterfaces$Proxy.class").toPath(), bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
