package com.yue.constant;

/**
 * Created by yue on 2018/5/28
 */
public class Common {

    public static final String[] uppercase;

    static {
        uppercase = new String[26];
        for (int i = 0; i < 26; i++) {
            uppercase[i] = String.valueOf((char) ('A' + i));
        }
    }

}
