package mytest.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.*;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.ServletContext;

/**
 * @BelongsProject: spring
 * @BelongsPackage: mytest
 * @Author: Lovezly
 * @CreateTime: 2020-07-12 20:32
 * @Description: 自定义SpringBean
 */
@Component
//@DependsOn("MyPreSpringBean")
public class MySpringBean implements BeanNameAware, BeanClassLoaderAware, BeanFactoryAware, EnvironmentAware,
		ResourceLoaderAware, ApplicationEventPublisherAware, ApplicationContextAware, ServletContextAware,
		BeanPostProcessor, InitializingBean, DisposableBean, BeanDefinitionRegistryPostProcessor {

	private String name;
	private int age;
	private boolean male;

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		System.out.println("invoke postProcessBeanDefinitionRegistry ------- implements BeanDefinitionRegistryPostProcessor");
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println("invoke postProcessBeanFactory ------- implements BeanDefinitionRegistryPostProcessor");
	}

	@Override
	public void setBeanName(String name) {
		System.out.println("invoke setBeanName ------- implements BeanNameAware");
	}

	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		System.out.println("invoke setBeanClassLoader ------- implements BeanClassLoaderAware");
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		System.out.println("invoke setBeanFactory --------  implements BeanFactoryAware");
	}

	@Override
	public void setEnvironment(Environment environment) {
		System.out.println("invoke setEnvironment ------- implements EnvironmentAware");
	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		System.out.println("invoke setResourceLoader ------- implements ResourceLoaderAware");
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		System.out.println("invoke setApplicationEventPublisher ------- implements ApplicationEventPublisherAware");
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		System.out.println("invoke setApplicationContext ------- implements ApplicationContextAware");
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		System.out.println("invoke setServletContext ------- implements ServletContextAware");
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("invoke postProcessBeforeInitialization ------- implements BeanPostProcessor --- " + beanName);
		return bean;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("invoke afterPropertiesSet ------- implements InitializingBean");
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("invoke postProcessAfterInitialization ------- implements BeanPostProcessor -- " + beanName);
		return bean;
	}

	@PostConstruct
	public void postConstruct() {
		System.out.println("invoke @postConstruct");
	}

	@PreDestroy
	public void preDestroy() {
		System.out.println("invoke @preDestroy");
	}

	public void myInitMethod() {
		System.out.println("invoke init-method");
	}

	public void myDestroyMethod() {
		System.out.println("invoke destroy-method");
	}

	@Override
	public void destroy() {
		System.out.println("invoke destroyMethod  ------  implements DisposableBean");
	}

	public MySpringBean() {
		System.out.println("Construct method without field");
	}

	public MySpringBean(String name, int age, boolean male) {
		System.out.println("Construct method with field");
		this.name = name;
		this.age = age;
		this.male = male;
	}

	public String getName() {
		System.out.println("getName method");
		return name;
	}

	public void setName(String name) {
		System.out.println("setName method");
		this.name = name;
	}

	public int getAge() {
		System.out.println("getAge method");
		return age;
	}

	public void setAge(int age) {
		System.out.println("setAge method");
		this.age = age;
	}

	public boolean isMale() {
		System.out.println("isMale method");
		return male;
	}

	public void setMale(boolean male) {
		System.out.println("setMale method");
		this.male = male;
	}

	@Override
	public String toString() {
		return "MySpringBean{" +
				"name='" + name + '\'' +
				", age=" + age +
				", male=" + male +
				'}';
	}
}