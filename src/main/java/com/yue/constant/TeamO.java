package com.yue.constant;

import lombok.Getter;

/**
 * Created by yue on 2018/5/28
 */
@Getter
public enum TeamO {

    now(1),
    once(2),;

    private int value;

    TeamO(int value) {
        this.value = value;
    }
}
