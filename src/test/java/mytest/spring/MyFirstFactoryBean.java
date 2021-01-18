package mytest.spring;

import mytest.domain.Visitor;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * @BelongsProject: spring
 * @BelongsPackage: mytest.spring
 * @Author: Lovezly
 * @CreateTime: 2020-08-22 10:36
 * @Description: FactoryBean类型的对象
 */
@Component
public class MyFirstFactoryBean implements FactoryBean<Visitor> {

	@Override
	public Visitor getObject() throws Exception {
		return new Visitor();
	}

	@Override
	public Class<?> getObjectType() {
		return Visitor.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
}
