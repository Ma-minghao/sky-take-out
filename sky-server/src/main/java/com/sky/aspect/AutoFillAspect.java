package com.sky.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 自定义切面
 */
@Aspect
@Component
@Slf4j
public class AutoFillAspect {
    //切入点
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut() {
    }

    //前置通知
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinpoint) {
        log.info("开始进行公共字段自动填充");

        //获取当前被拦截到的方法上的数据库操作类型

        //获取当前被拦截到的方法的参数--实体对象


    }

}
