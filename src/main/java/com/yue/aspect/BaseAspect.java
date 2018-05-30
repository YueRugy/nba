package com.yue.aspect;

import com.yue.annotation.ControllerLogAnnotation;
import com.yue.util.ClassUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yue on 2018/5/30
 */
public class BaseAspect {
    Logger logger = LogManager.getLogger(getClass());
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    /**
     * 获取请求头部信息
     */
    Map<String, String> getHeadersInfo(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }

    /**
     * 获取注解中对方法的描述信息
     *
     * @param joinPoint 切点
     * @return 方法描述
     * @throws Exception
     */


    String getMethodDescription(JoinPoint joinPoint)
            throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description = method.getAnnotation(ControllerLogAnnotation.class)
                            .description();
                    break;
                }
            }
        }
        return description;
    }


    /**
     * 打印方法参数值  基本类型直接打印，非基本类型需要重写toString方法
     *
     * @param paramsArgsName  方法参数名数组
     * @param paramsArgsValue 方法参数值数组
     */
    void logParam(String[] paramsArgsName, Object[] paramsArgsValue) {
        if (ArrayUtils.isEmpty(paramsArgsName) || ArrayUtils.isEmpty(paramsArgsValue)) {
            logger.info("该方法没有参数");
            return;
        }
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < paramsArgsName.length; i++) {
            //参数名
            String name = paramsArgsName[i];
            //参数值
            Object value = paramsArgsValue[i];
            buffer.append(name).append(" = ");
            if (ClassUtil.isPrimitive(value.getClass())) {
                buffer.append(value).append("  ,");
            } else {
                buffer.append(value.toString()).append("  ,");
            }
        }
        logger.info(buffer.toString());
    }

    void errorLog(JoinPoint joinPoint, Throwable e) {
        try {
            String methodDesc = getMethodDescription(joinPoint);
            logger.error(methodDesc + " failed!");
            logger.error((joinPoint.getTarget().getClass().getName() + "."
                    + joinPoint.getSignature().getName() + "()")
                    + " occurred exception ", e);

            //写数据库日志
        } catch (Exception ex) {
            //记录本地异常日志
            logger.error("Do after throwing occurred exception:{}", ex.getMessage());

        }
    }

}
