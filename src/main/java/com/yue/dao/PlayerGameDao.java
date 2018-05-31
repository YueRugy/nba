package com.yue.dao;

import com.yue.entity.PlayerGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by yue on 2018/5/31
 */
@Repository
public interface PlayerGameDao extends JpaRepository<PlayerGame, Integer> {
}
