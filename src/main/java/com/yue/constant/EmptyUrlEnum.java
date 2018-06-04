package com.yue.constant;

import lombok.Getter;

/**
 * Created by yue on 2018/6/1
 */
@Getter
public enum EmptyUrlEnum {
    player(1),
    game(2),;
    private int value;

    EmptyUrlEnum(int value) {
        this.value = value;
    }
}
