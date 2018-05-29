package com.yue.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by yue on 2018/5/28
 */

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Player implements Serializable{
    @Id
    @GeneratedValue
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


}
