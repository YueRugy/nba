package com.yue.jsonBean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yue on 2018/5/20
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Payload implements Serializable {
    private Object league;
    private Object session;
    private List<Players> players;
}
