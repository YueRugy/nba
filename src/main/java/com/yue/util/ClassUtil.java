package com.yue.util;

import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

/**
 * Created by yue on 2018/5/30
 */
public class ClassUtil {

    /**
     * 使用javassist来获取方法参数名称
     *
     * @param className  类名
     * @param methodName 方法名
     * @return
     * @throws Exception
     */
    public static String[] getFieldsName(String className, String methodName) throws Exception {
        Class<?> clazz = Class.forName(className);
        String clazz_name = clazz.getName();
        ClassPool pool = ClassPool.getDefault();
        ClassClassPath classPath = new ClassClassPath(clazz);
        pool.insertClassPath(classPath);

        CtClass ctClass = pool.get(clazz_name);
        CtMethod ctMethod = ctClass.getDeclaredMethod(methodName);
        MethodInfo methodInfo = ctMethod.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        if (attr == null) {
            return null;
        }
        String[] paramsArgsName = new String[ctMethod.getParameterTypes().length];
        int pos = Modifier.isStatic(ctMethod.getModifiers()) ? 0 : 1;
        for (int i = 0; i < paramsArgsName.length; i++) {
            paramsArgsName[i] = attr.variableName(i + pos);
        }
        return paramsArgsName;
    }


    /**
     * 判断是否为基本类型：包括String
     *
     * @param clazz clazz
     * @return true：是;     false：不是
     */
    public static boolean isPrimitive(Class<?> clazz) {
        return clazz.isPrimitive() || clazz == String.class;
    }
}
