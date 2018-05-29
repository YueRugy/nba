package com.yue.constant;

import lombok.Getter;

/**
 * Created by yue on 2018/5/19
 */
@Getter
public enum TeamD {
    east(1),
    west(2),;

    private int code;


    TeamD(int code) {
        this.code = code;
    }
}
