package com.yue.constant;

import lombok.Getter;

/**
 * Created by yue on 2018/5/22.
 */
@Getter
public enum GameSuccess {

    success(1),
    faild(2),;

    private int code;

    GameSuccess(int code) {
        this.code = code;
    }

}
