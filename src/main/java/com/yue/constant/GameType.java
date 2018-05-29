package com.yue.constant;

import lombok.Getter;

/**
 * Created by yue on 2018/5/29
 */
@Getter
public enum GameType {
    season(1),//常规赛
    playoff(2),//季后赛
    ;
    private int value;

    GameType(int value) {
        this.value = value;
    }
}
