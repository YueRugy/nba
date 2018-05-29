package com.yue.constant;

import lombok.Getter;

/**
 * Created by yue on 2018/5/28
 */
@Getter
public enum TeamP {
    southeast(1),//东南
    intermediate(2),//中
    atlantic(3),//大西洋
    tpo(4),//太平洋
    northwest(5),//西北
    southwest(6)//西南
    ;
    private int code;

    TeamP(int code) {
        this.code = code;
    }

}
