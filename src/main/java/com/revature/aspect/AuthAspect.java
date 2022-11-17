package com.revature.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Order(1)
@Component("authAspect")
public class AuthAspect {

	/*
	 * Let's specifically target me thods that are annotated with @Manager for our
	 * Pointcut expression.
	 */
	@Pointcut(value = "@annotation(com.revature.aspect.Manager)")
	public void targetAnnotatedMethods() {}
	
	/*
	 * Now let's make our around advice. The idea is that if a client is not authenticated,
	 * they should not get a list of polkamans back. 
	 */
	
	@Around("targetAnnotatedMethods()")
	public Object authAdvice(ProceedingJoinPoint pjp) {
		Object valueToReturn = null;
		
		int indexOfArg = 0;
		
		/*
		 * We can use a special joinpoint called a "CodeSignature" to access parameter
		 * names.
		 */
		CodeSignature codeSignature = (CodeSignature) pjp.getSignature();
		String[] paramNames = codeSignature.getParameterNames();
		
		for(int i = 0; i < paramNames.length; i++) {
			if(paramNames[i].equals("auth")) indexOfArg = i;
		}
		
		//Grab the arguments that were passed to the method.
		Object[] args = pjp.getArgs();
		
		//Check my argument's value:
		if(args[indexOfArg].equals("authenticated")) {
			try {
				valueToReturn = pjp.proceed();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}else {
			valueToReturn = ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		
		return valueToReturn;
		
	}
}
