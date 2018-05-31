package com.yue.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by yue on 2018/5/30
 */
@Entity(name = "player_game")
@Getter
@Setter
@EqualsAndHashCode(exclude = {"team"})
@ToString
@JsonIgnoreProperties(value = {"team", "opponentTeam", "hibernateLazyInitializer", "hibernateLazyInitializer", "handler", "fieldHandler"})
public class PlayerGame implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)//可选属性optional=false,表示company不能为空
    @JoinColumn(name = "t_id")//设置在employee表中的关联字段(外键)
    private Team team;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)//可选属性optional=false,表示company不能为空
    @JoinColumn(name = "g_id")//设置在employee表中的关联字段(外键)
    private Game game;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)//可选属性optional=false,表示company不能为空
    @JoinColumn(name = "p_id")//设置在employee表中的关联字段(外键)
    private Player player;



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

    private BigDecimal realRate;
}
