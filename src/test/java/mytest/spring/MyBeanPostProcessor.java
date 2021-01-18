package mytest.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @BelongsProject: spring
 * @BelongsPackage: mytest.spring
 * @Author: Lovezly
 * @CreateTime: 2020-07-12 22:18
 * @Description: My Custom BeanPostProcessor
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("invoke postProcessBeforeInitialization ------- implements BeanPostProcessor  ------  " +
				"MyBeanPostProcessor -- " + beanName);
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("invoke postProcessAfterInitialization ------- implements BeanPostProcessor  --------  " +
				"MyBeanPostProcessor -- " +beanName);
		return bean;
	}
}
