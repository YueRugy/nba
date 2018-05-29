package com.yue.util;

/**
 * Created by yue on 2018/5/22
 */
public class StringUtil {

    //去数字
    public static String removeNumber(String str) {
        if (str == null) {
            return null;
        }

        return str.replaceAll("\\d+", "");


    }

    //去掉指定字符

    public static String removeSpecifiedChar(String str, String specified) {
        if (str == null) {
            return null;
        }

        return str.replaceAll(specified, "");


    }

    public static String rightTrim(String str) {
        if (str == null) {
            return null;
        }

        return str.replaceAll("\\s*$", "");
    }


    public static String leftTrim(String str) {
        if (str == null) {
            return null;
        }

        return str.replaceAll("^\\s*", "");
    }

    public static String rightAndLeftTrim(String str) {
        if (str == null) {
            return null;
        }

        return str.replaceAll("^\\s*(.*?)\\s*$", "$1");
    }
}
