package com.example.springbootdemo.Aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author HouXudong
 * @date 2019-09-05
 */
@Component
@Aspect
public class ExceptionAspect {
    @Pointcut("execution(* com.example.springbootdemo.service.UserService.*(..))")
    public void pointut() {
    }

    @Before("pointut()")
    public Object handler(JoinPoint joinPoint) throws Throwable {
        System.out.println("this is a before aspect!");
        Object proceed = joinPoint.getSignature();
        System.out.println(proceed);
        return proceed;
    }
}
