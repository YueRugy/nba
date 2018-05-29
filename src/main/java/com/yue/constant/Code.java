package com.yue.constant;

import lombok.Getter;

/**
 * Created by yue on 2017/9/9
 */
@Getter
public enum Code {
    SUCCESS(200),//成功
    ERROR(400),//
    NEED_LOGIN(302),//
    ;
    private int code;

    Code(int code) {
        this.code = code;
    }
}
