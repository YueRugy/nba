package com.yue.dao;

import com.yue.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by yue on 2018/5/28
 */
@Repository
public interface PlayerDao extends JpaRepository<Player, Integer> {
    Player findByUrl(String href);
}
