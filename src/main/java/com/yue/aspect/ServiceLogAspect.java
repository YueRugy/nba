package com.yue.aspect;

import com.yue.annotation.ServiceLogAnnotation;
import com.yue.util.ClassUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;

/**
 * Created by yue on 2018/5/30
 */
@Aspect
@Configuration
@Order(2)
public class ServiceLogAspect extends BaseAspect {

    @Pointcut("execution(public * com.yue.service..*.*(..))")
    public void servicePointcut() {
    }

    @Before("servicePointcut()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        startTime.set(System.currentTimeMillis());
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        logger.info("class_name : " + className);
        logger.info("method_name : " + methodName);
        Object[] paramsArgsValues = joinPoint.getArgs();
        String[] paramNames = ClassUtil.getFieldsName(className, methodName);
        logParam(paramNames, paramsArgsValues);

    }

    @AfterReturning(returning = "ret", pointcut = "servicePointcut()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        logger.info("返回内容: " + ret);
        logger.info("花费时间 : "
                + (System.currentTimeMillis() - startTime.get()));

        //写数据库日志
    }


    @AfterThrowing(pointcut = "servicePointcut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        errorLog(joinPoint, e);
    }


    @Override
    String getDescription(Method method) {
        return method.getAnnotation(ServiceLogAnnotation.class) == null ?
                "" : method.getAnnotation(ServiceLogAnnotation.class).description();
    }
}
