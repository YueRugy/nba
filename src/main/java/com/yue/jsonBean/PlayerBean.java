package com.yue.jsonBean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by yue on 2018/5/20.
 */

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class PlayerBean implements Serializable {

    private String code;
    private String country;
    private String countryEn;
    private String displayAffiliation;
    private String displayName;
    private String displayNameEn;
    private String dob;
    private String draftYear;
    private String experience;
    private String firstInitial;
    private String firstName;
    private String firstNameEn;
    private String lastName;
    private String lastNameEn;
    private String leagueId;
    private String playerId;
    private String position;
    private String schoolType;
    private double weight;

    private String height;
    private Integer jerseyNo;


}
