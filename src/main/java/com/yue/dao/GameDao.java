package com.yue.dao;

import com.yue.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by yue on 2018/5/29
 */
@Repository
public interface GameDao extends JpaRepository<Game, Integer> {
}
