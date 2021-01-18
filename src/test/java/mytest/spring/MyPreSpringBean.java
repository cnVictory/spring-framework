package mytest.spring;

import org.springframework.stereotype.Component;

/**
 * @BelongsProject: spring
 * @BelongsPackage: mytest.spring
 * @Author: Lovezly
 * @CreateTime: 2020-07-18 10:39
 * @Description: 测试DependsON
 */
@Component
public class MyPreSpringBean {

	static {
		System.out.println("测试一下DependsOn的效果");
	}

	public MyPreSpringBean() {
		System.out.println("myPreSpringBean Construct");
	}
}
