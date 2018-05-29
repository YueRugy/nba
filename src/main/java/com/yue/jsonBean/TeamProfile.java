package com.yue.jsonBean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by yue on 2018/5/
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TeamProfile implements Serializable {
    private String abbr;
    private String city;
    private String cityEn;
    private String code;
    private String conference;
    private String displayAbbr;
    private String displayConference;
    private String division;
    private String id;
    private String isAllStarTeam;
    private String isLeagueTeam;
    private String leagueId;
    private String name;
    private String nameEn;
}
