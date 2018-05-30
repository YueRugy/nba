package com.yue.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;


/**
 * Created by yue on 2018/5/30
 */
@Aspect
@Configuration
public class ControllerLogAspect extends BaseAspect {


    @Pointcut("execution(public * com.yue.controller..*.*(..))")
    public void controllerPointcut() {
    }

    @Before("controllerPointcut()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {

        startTime.set(System.currentTimeMillis());

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("HEADERS : " + getHeadersInfo(request));
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : "
                + joinPoint.getSignature().getDeclaringTypeName() + "."
                + joinPoint.getSignature().getName());
        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
        logger.info("PARAMETERS : " + request.getParameterMap());

    }

    @AfterReturning(returning = "ret", pointcut = "controllerPointcut()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        logger.info("RESPONSE : " + ret);
        logger.info("SPEND TIME : "
                + (System.currentTimeMillis() - startTime.get()));

        //写数据库日志
    }

    @AfterThrowing(pointcut = "controllerPointcut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        errorLog(joinPoint, e);

    }


}
