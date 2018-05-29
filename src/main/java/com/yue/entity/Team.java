package com.yue.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by yue on 2018/5/19
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Team implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    @Column(name = "name_en")
    private String nameEn;
    private String simple;
    private Integer direction;//东西部
    private Integer part;//分区
    private Integer once;//是否是以前的球队

    private String url;


}
