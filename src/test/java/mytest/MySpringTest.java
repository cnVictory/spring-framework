package mytest;

import mytest.domain.Visitor;
import mytest.transaction.tx.MyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:mytest/AppConfig.xml"})
public class MySpringTest {

	@Test
	public void test() {
		System.out.println("===============> 开始进行测试 <=====================");
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
//		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("mytest/AppConfig.xml");
		MyService myService = (MyService) ac.getBean("myServiceImpl");
		System.out.println("-------------------------------------------------------------------------");
		myService.delete("test");
		myService.insert(new Visitor());
		myService.update("test", new Visitor());
		myService.select("test");
		ac.destroy();
	}
}
