package com.yue.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by yue on 2018/5/28
 */

@Entity(name = "player")
@Getter
@Setter
@EqualsAndHashCode(exclude = "pgList")
@ToString
@JsonIgnoreProperties(value = {"pgList", "hibernateLazyInitializer", "hibernateLazyInitializer", "handler", "fieldHandler"})
public class Player implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(name = "name_en")
    private String nameEn;
    private String simple;
    private Double height;
    private Double weight;
    private String pos;
    @Temporal(TemporalType.DATE)
    private Date date;

    private String url;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PlayerGame> pgList;


}
