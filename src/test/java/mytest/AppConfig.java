package mytest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * 1.只加载@EnableTransactionManagement事务注解的时候， 在IOC容器中会注册一个internalAutoProxyCreator到beanDefinitionMap中
 * 	 这个 internalAutoProxyCreator的真实对象是 InfrastructureAdvisorAutoProxyCreator
 * 	 并且在IOC容器的beanPostProcessors列表中，会注册InfrastructureAdvisorAutoProxyCreator来进行后置处理
 * 2.只加载@EnableAspectJAutoProxy AOP注解的时候，在IOC容器中也是会注册一个internalAutoProxyCreator到beanDefinitionMap中
 * 	 并且这个 internalAutoProxyCreator的真实对象是 AnnotationAwareAspectJAutoProxyCreator
 * 	 并且在IOC容器的beanPostProcessor列表中，会注册这个 AnnotationAwareAspectJAutoProxyCreator作为后置处理器
 * 3.当事务和AOP的2个注解同时存在的时候，由于internalAutoProxyCreator这个key是唯一的，所以只会加载一个，但是又由于优先级的问题，最终使用的是
 *   AnnotationAwareAspectJAutoProxyCreator 和这个类
 *   并且这个时候在beanPostProcessors列表中，注册的也是AnnotationAwareAspectJAutoProxyCreator作为后置处理器
 *
 * @BelongsProject: spring
 * @BelongsPackage: mytest
 * @Author: Lovezly
 * @CreateTime: 2020-07-12 20:44
 * @Description: 配置类
 */
@Configuration
//@ComponentScan(value = "mytest", excludeFilters = {@ComponentScan.Filter(type = FilterType.REGEX, pattern = {"mytest.spring.MyExcludeBean"})})
//@ComponentScan(value = {"mytest.spring","mytest.domain"})
@ComponentScan(value = {"mytest.transaction"})
@EnableTransactionManagement
//@EnableAspectJAutoProxy
public class AppConfig {

	@Bean
	public DataSource dataSource() {
		return new DriverManagerDataSource("jdbc:mysql://192.168.44.166:3307/test", "root", "admin123");
	}

	// 其中 dataSource 框架会自动为我们注入
	@Bean
	public PlatformTransactionManager txManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

}
