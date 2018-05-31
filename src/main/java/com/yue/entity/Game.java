package com.yue.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by yue on 2018/5/29
 */

@Entity(name = "game")
@Getter
@Setter
@EqualsAndHashCode(exclude = {"team"})
@ToString
@JsonIgnoreProperties(value = {"team", "opponentTeam", "hibernateLazyInitializer", "hibernateLazyInitializer", "handler", "fieldHandler"})
public class Game implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)//可选属性optional=false,表示company不能为空
    @JoinColumn(name = "t_id")//设置在employee表中的关联字段(外键)
    private Team team;
    private Date date;

    private Integer result;
    private Integer pri;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)//可选属性optional=false,表示company不能为空
    @JoinColumn(name = "o_id")//设置在employee表中的关联字段(外键)
    private Team opponentTeam;

    @Column(name = "shoot_rate", precision = 5, scale = 3)
    private BigDecimal shootRate;

    private Integer shoot;
    private Integer shootNumber;
    @Column(precision = 5, scale = 3)
    private BigDecimal tShootRate;
    private Integer tShoot;
    private Integer tShootNumber;

    @Column(precision = 5, scale = 3)
    private BigDecimal pShootRate;
    private Integer pShoot;
    private Integer pShootNumber;


    private Integer rebounds;
    private Integer tRebound;
    private Integer oRebound;
    private Integer assists;
    private Integer steal;
    private Integer block;
    private Integer miss;
    private Integer foul;
    private Integer score;

    private Integer gameType;
    private String url;

    private String gameTime;


    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PlayerGame> pgList;
}
