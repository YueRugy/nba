package com.yue.jsonBean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by yue on 2018/5/20
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class PlayerTeam implements Serializable {
    private TeamBean teamBean;
    private PlayerBean playerBean;
}
