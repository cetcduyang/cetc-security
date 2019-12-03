package com.cetc.web.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.util.Date;

@Aspect
//@Component
public class TimeAspect {

    //在什么时候起作用，在哪个方法上起作用
    @Around("execution(* com.cetc.web.controller.UserController.*(..))")
    public Object handlControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("time aspect start");
        Object[] args = pjp.getArgs(); //获得方法中的参数
        for(Object arg:args){
            System.out.println("arg--"+arg.toString());
        }
        long start = new Date().getTime();
        Object object = pjp.proceed();  //这里object是方法的返回值
        System.out.println("time aspect 耗时："+(new Date().getTime() - start) + object.toString());
        return object;
    }

}
