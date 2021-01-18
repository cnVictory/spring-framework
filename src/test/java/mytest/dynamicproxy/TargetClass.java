package mytest.dynamicproxy;

/**
 * @BelongsProject: spring
 * @BelongsPackage: mytest.dynamicproxy
 * @Author: Lovezly
 * @CreateTime: 2020-07-25 07:42
 * @Description: 原有目标对象
 */
public class TargetClass implements TargetInterface{

	@Override
	public void print(String string) {
		System.out.println("print : " + string);
	}

	@Override
	public void println(String str) {
		System.out.println("println: " + str);
	}
}
