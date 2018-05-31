package com.yue.dao;

import com.yue.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yue on 2018/5/19
 */
@Repository
public interface TeamDao extends JpaRepository<Team, Integer> {


    List<Team> findByDirection(int code);

    List<Team> findByDirectionIsNull();

    List<Team> findByOnce(int value);

    Team findBySimple(String href);
}
