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
public class PlayerList implements Serializable {

    private Object context;
    private Object error;
    private Payload payload;
    private List<PlayerTeam> list;
    private String timestamp;


}
