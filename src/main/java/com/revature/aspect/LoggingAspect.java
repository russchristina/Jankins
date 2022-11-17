package com.revature.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/*
 * Recall that Spring AOP allows us to modularize cross-cutting concerns (e.g. logging,
 * security, etc.) in an application. In order to create a class which contains advice
 * (code that will be injected at specified points), we should use the @Aspect annotation.
 * Note that @Aspect comes from AspectJ.
 * 
 * Also note that we want Spring to make a bean of our Aspect class. That said, we will
 * make this a @Component.
 */
@Aspect
@Component("loggingAspect")
public class LoggingAspect {
	
	/*
	 * We're going to log to a file using Logback. That said, I'm using SL4J (Simple
	 * Logging Facade 4 Java) as it provides an interface that I can program against.
	 * This means that I can do less refactoring if I ever switch my logging
	 * implementation.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

	/*
	 * We should decide where we would like to inject the advice. In order to do this,
	 * we can use Pointcut expressions. This pointcut expression denotes that I want
	 * to inject advice within all types in my controller layer.
	 */
	@Pointcut("within(com.revature.controller..*)")
	public void logWithinControllers() {}
	
	/*
	 * Now let's see our different types of advice!
	 * 
	 * We have: @Before, @After, @AfterReturning, @AfterThrowing, @Around
	 */
	
	/*
	 * Technically speaking, you can use a pointcut expression with your advice annotation,
	 * but if you have already bound a pointcut expression to a method, you can just use
	 * the method as the value for the advice.
	 */
	@Before("logWithinControllers()")
	public void beforeAdvice(JoinPoint jp) {
		/*
		 * Your advice goes here!
		 */
		LOGGER.info("This is before advice for my controller.");
	}
	
	@After("logWithinControllers()")
	public void afterAdvice(JoinPoint jp) {
		LOGGER.info("This is after advice for my controller.");
	}
	
	/*
	 * With @AfterReturning advice, you have access to the value that the method returned.
	 * You can access it by binding the returning attribute to a method parameter.
	 * 
	 * Also note that ALL advice has access to a JoinPoint. That JoinPoint allows you to 
	 * access information about the method that has been called.
	 */
	@AfterReturning(value = "logWithinControllers()", returning = "returnedValue")
	public void afterReturningAdvice(JoinPoint jp, Object returnedValue) {
		String methodName = jp.getSignature().getName();
		LOGGER.info("The " + methodName + " has been called and it has returned " +
		returnedValue);
	}
	
	@AfterThrowing(value = "logWithinControllers()", throwing = "thrownException")
	public void afterThrowingAdvice(JoinPoint jp, Exception thrownException) {
		String methodName = jp.getSignature().getName();
		LOGGER.info("The " + methodName + " has thrown a " + thrownException + ".");
	}
	
	/*
	 * Around advice is so powerful that just by using it I completely stop methods
	 * from being invoked. That said, you can explicitly proceed with the method's
	 * normal invocation by using a special JoinPoint called a ProceedingJoinPoint.
	 * 
	 * Around advice for something like logging is overkill. I've only put this here for
	 * your notes.
	 */
//	@Around("logWithinControllers()")
//	public Object aroundAdvice(ProceedingJoinPoint pjp) {
//		/*
//		 * Ordinarily, you are responsible for returning the value that calling "proceed"
//		 * returns.
//		 */
//		
//		Object returnedObject = null;
//		
//		try {
//			//Calling proceed actually proceeds with the method invocation.
//			returnedObject = pjp.proceed();
//		} catch (Throwable e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return returnedObject;
//	}
}
