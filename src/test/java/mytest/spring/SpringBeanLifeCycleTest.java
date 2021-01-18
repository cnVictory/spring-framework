package mytest.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @BelongsProject: spring
 * @BelongsPackage: mytest
 * @Author: Lovezly
 * @CreateTime: 2020-07-12 20:30
 * @Description: SpringBean生命周期的测试类
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:mytest/AppCOnfig.xml"})
public class SpringBeanLifeCycleTest {

	@Test
	public void test() {
		System.out.println("===============> 开始进行测试 <=====================");
//		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("mytest/AppConfig.xml");
		MySpringBean mySpringBean = (MySpringBean) ac.getBean("mySpringBean");

		System.out.println("---------------------");
		ac.destroy();
	}
}
