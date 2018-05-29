package com.yue.constant;

import lombok.Getter;

/**
 * Created by yue on 2018/5/19
 */
@Getter
public enum UserCode {
    usernameEmpty(1),
    passwordEmpty(2),
    success(3),
    usernameExists(4),;

    private int code;

    UserCode(int code) {
        this.code = code;
    }

}
