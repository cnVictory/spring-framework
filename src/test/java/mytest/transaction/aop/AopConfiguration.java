package mytest.transaction.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AopConfiguration {

	@Pointcut("execution(* mytest.transaction.tx.*.**(..))")
	public void pointcut() {

	}

	@Before("pointcut()")
	public void before() {
		System.out.println("=============== before ==============");
	}

	@After("pointcut()")
	public void after() {
		System.out.println("=============== after ==============");
	}

	@AfterReturning("pointcut()")
	public void afterReturning() {
		System.out.println("=============== afterReturning ==============");
	}


	@AfterThrowing("pointcut()")
	public void afterThrowing() {
		System.out.println("=============== afterThrowing ==============");
	}

	@Around("pointcut()")
	public Object around(ProceedingJoinPoint joinPoint) {
		Object proceed = null;
		System.out.println("=============== around  before ==============");
		String methodName = joinPoint.getSignature().getName();
		System.out.println(" around , method name = " + methodName);
		try {
			proceed = joinPoint.proceed();
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
		System.out.println("=============== around after ==============");
		return proceed;
	}
}
