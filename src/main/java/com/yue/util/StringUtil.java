package com.yue.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yue on 2018/5/22
 */
public class StringUtil {

    //去数字
    public static String removeNumber(String str) {
        if (str == null) {
            return null;
        }

        final String regex = "\\d+";

        return removeSpecifiedChar(str, regex);

    }


    public static String removeChinese(String str){
        if (str == null) {
            return null;
        }

        final String regex ="[\u4e00-\u9fa5]";

        return removeSpecifiedChar(str, regex);
    }

    //去掉指定字符

    public static String removeSpecifiedChar(String str, String regex) {
        if (str == null) {
            return null;
        }
        Pattern pat = Pattern.compile(regex);
        Matcher mat = pat.matcher(str);
        return mat.replaceAll("");


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
