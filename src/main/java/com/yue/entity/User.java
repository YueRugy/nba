package com.yue.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by yue on 2018/5/16
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class User implements Serializable{
    @Id
    @GeneratedValue
    private Integer id;
    private String username;
    private String password;
}
