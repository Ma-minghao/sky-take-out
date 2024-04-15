package com.sky.aspect;


import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import javassist.bytecode.SignatureAttribute;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.Joinpoint;
import org.apache.ibatis.binding.MapperMethod;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 自定义切面
 */
@Aspect
@Component
@Slf4j
public class AutoFillAspect {
    //切入点
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut() {}

    //前置通知
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinpoint) {
        log.info("开始进行公共字段自动填充");

        //获取当前被拦截到的方法上的数据库操作类型
        MethodSignature signature = (MethodSignature)joinpoint.getSignature();      //方法的签名
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);    //获得方法上的注解对象
        OperationType operationType = autoFill.value();                             //获得数据库操作类型

        //获取当前被拦截到的方法的参数--实体对象
        Object[] args = joinpoint.getArgs();
        if(args==null || args.length==0 ){
            return;
        }
        Object entity = args[0];
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();

        //准备赋值的数据
        switch (operationType){
            case INSERT:
                //为4个公共字段属性属性赋值
                try {
                    Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME,LocalDateTime.class);
                    Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER,Long.class);
                    Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME,LocalDateTime.class);
                    Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER,Long.class);

                    setCreateTime.invoke(entity,now);
                    setCreateUser.invoke(entity,currentId);
                    setUpdateTime.invoke(entity,now);
                    setUpdateUser.invoke(entity,currentId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                log.info("公共字段自动填充[insert]");
                break;
            case UPDATE:
                //更新操作，为这些属性赋值
                try {
                    Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME,LocalDateTime.class);
                    Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER,Long.class);

                    setUpdateTime.invoke(entity,now);
                    setUpdateUser.invoke(entity,currentId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                log.info("公共字段自动填充[update]");
                break;
            default:
                break;
        }
    }

}
