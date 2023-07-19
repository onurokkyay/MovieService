package com.krawen.movieservice.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
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
		Object[] args = joinPoint.getArgs();

		LOGGER.info("Logging retrievePopularMovies method.");
		LOGGER.info("Method Name: {}", methodName);
		LOGGER.info("Declaring Type: {}", declaringType);
		LOGGER.info("Method Signature: {}", methodSignature);

		if (args != null && args.length > 0) {
			for (Object arg : args) {
				LOGGER.info("Method Argument: {}", arg);
			}
		}
	}

}
