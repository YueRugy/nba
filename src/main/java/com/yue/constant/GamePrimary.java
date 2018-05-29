package com.yue.constant;

import lombok.Getter;

/**
 * Created by yue on 2018/5/22.
 */
@Getter
public enum GamePrimary {
    yes(1),
    no(2),;
    private int code;

    GamePrimary(int code) {
        this.code = code;
    }
}
