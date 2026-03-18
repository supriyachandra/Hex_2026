package com.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogAspect {

    @Before("execution(* com.repository.CartRepository.*(..))")
    public void logBefore(JoinPoint joinPoint){
        String methodName= joinPoint.getSignature().getName();
        System.out.println("--> "+ methodName+ " Started");
    }

    @After("execution(* com.repository.CartRepository.*(..))")
    public void logAfter(JoinPoint joinPoint){
        String methodName= joinPoint.getSignature().getName();
        System.out.println("--> "+ methodName+ " Ended");
    }
}
