package mytest.spring;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @BelongsProject: spring
 * @BelongsPackage: mytest.spring
 * @Author: Lovezly
 * @CreateTime: 2020-07-18 11:48
 * @Description: 被排除的Bean
 */
@Component
@Scope("prototype")
public class MyExcludeBean {

	static {
		System.out.println("My exclude bean");
	}

	public MyExcludeBean() {
		System.out.println("exclude bean construct");
	}
}
