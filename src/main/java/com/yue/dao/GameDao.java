package com.yue.dao;

import com.yue.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yue on 2018/5/29
 */
@Repository
public interface GameDao extends JpaRepository<Game, Integer> {


    List<Game> findByUrl(String query);

    @Query(value = "SELECT * FROM game WHERE url IN (SELECT url FROM " +
            "(SELECT url,count(url) FROM game GROUP BY url" +
            " HAVING count(url)>1 )  AS s1) AND o_id = t_id ORDER BY url", nativeQuery = true)
    List<Game> selectO();

    Game findByUrlAndTId(String query, Integer id);
}
