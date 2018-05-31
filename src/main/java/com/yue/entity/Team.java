package com.yue.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Created by yue on 2018/5/19
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode(exclude = {"list"})
@ToString
@JsonIgnoreProperties(value = {"list", "oList", "hibernateLazyInitializer", "hibernateLazyInitializer", "handler", "fieldHandler"})
public class Team implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(name = "name_en")
    private String nameEn;
    private String simple;
    private Integer direction;//东西部
    private Integer part;//分区
    private Integer once;//是否是以前的球队

    private String url;
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Game> list;

    @OneToMany(mappedBy = "opponentTeam", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Game> oList;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PlayerGame> pgList;


}
