package com.krawen.movieservice.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

	@Before(value = "execution(* com.krawen.movieservice.controller..*(..))")
	public void beforeAdvice(JoinPoint joinPoint) {
		LOGGER.info("Before execution: " + joinPoint);
		logRetrievePopularMovies(joinPoint);
	}

	@After(value = "execution(* com.krawen.movieservice.controller..*(..))")
	public void afterAdvice(JoinPoint joinPoint) {
		LOGGER.info("After execution: " + joinPoint);
		logRetrievePopularMovies(joinPoint);
	}

	public void logRetrievePopularMovies(JoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().getName();
		String declaringType = joinPoint.getSignature().getDeclaringTypeName();
		Signature signature = joinPoint.getSignature();
		String methodSignature = signature.toLongString();

		LOGGER.info("Logging retrievePopularMovies method.");
		LOGGER.info("Method Name: {}", methodName);
		LOGGER.info("Declaring Type: {}", declaringType);
		LOGGER.info("Method Signature: {}", methodSignature);
		logArgs(joinPoint.getArgs());

	}

	@AfterReturning(pointcut = "execution(* com.krawen.movieservice.controller..*(..))", returning = "result")
	public void logAfter(JoinPoint joinPoint, Object result) {

		LOGGER.info("Method returned:" + joinPoint.getSignature().getName() + ", Result: " + result.getClass().getName()
				+ " -->" + result);

	}

	@Pointcut("@annotation(Log)")
	public void logPointcut() {
	}

	@Before("logPointcut()")
	public void logAllMethodCallsAdvice(JoinPoint joinPoint) {
		logArgs(joinPoint.getArgs());
		LOGGER.info("@Log Before " + joinPoint.getSignature().getName());
	}
	
    @Pointcut("within(com.krawen.movieservice.controller.UserController)")
    public void validationPointcut(){}
    
    @Around("validationPointcut()")
    public void aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
    	LOGGER.info("Validation");
        //int arg = (int) joinPoint.getArgs()[0];
        //if (arg < 0)
        //    throw new RuntimeException("Argument should not be negative");
        //else
            joinPoint.proceed();
    }

	private void logArgs(Object[] args) {
		if (args != null && args.length > 0) {
			for (Object arg : args) {
				LOGGER.info("Method Argument: {}", arg);
			}
		}
	}

}
