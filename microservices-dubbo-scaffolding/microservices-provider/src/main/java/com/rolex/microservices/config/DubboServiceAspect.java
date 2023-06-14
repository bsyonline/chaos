package com.rolex.microservices.config;

import com.rolex.microservices.common.ReturnResult;
import com.rolex.microservices.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@Aspect
@Slf4j
@Component
public class DubboServiceAspect {

    /**
     * 表达式意思为：所有remote包下的，返回ReturnResult 类的，才会被使用
     */
    @Pointcut("execution(com.rolex.microservices.common.ReturnResult com.rolex..remote.*.*(..))")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void beginTransaction() {
    }

    @After("pointcut()")
    public void commit() {
    }

    @AfterReturning(value = "pointcut()", returning = "returnObject")
    public void afterReturning(JoinPoint joinPoint, Object returnObject) {
    }

    @AfterThrowing("pointcut()")
    public void afterThrowing() {
    }


    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (ServiceException e) {
            //自定义异常，需要封装
            ReturnResult result = ReturnResult.fail(e.getErrorType(), e.getMessage());
            return result;
        } catch (Exception e) {
            //自定义异常，需要封装
            ReturnResult result = ReturnResult.fail(e.getMessage());
            return result;
        } catch (Throwable e) {
            //自定义异常，需要封装
            ReturnResult result = ReturnResult.fail(e.getMessage());
            return result;
        }
    }
}
