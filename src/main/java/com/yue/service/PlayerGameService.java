package com.yue.service;

import com.yue.dao.GameDao;
import com.yue.dao.PlayerGameDao;
import com.yue.dao.TeamDao;
import com.yue.entity.Game;
import com.yue.task.DouTask;
import com.yue.task.SingleTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by yue on 2018/5/31
 */
@Service
public class PlayerGameService {
    private final PlayerGameDao playerGameDao;
    private final GameDao gameDao;
    private final TeamDao teamDao;

    @Autowired
    public PlayerGameService(PlayerGameDao playerGameDao, GameDao gameDao, TeamDao teamDao) {
        this.playerGameDao = playerGameDao;
        this.gameDao = gameDao;
        this.teamDao = teamDao;
    }

    public void init() {


    }

    public void refactor() {
        int endIndex = 42426;
        int startIndex = 1;

        String base = "http://www.stat-nba.com/";

        ExecutorService single = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() / 2 + 1);
        ExecutorService dou = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() / 2 + 1);

        for (int i = startIndex; i <= endIndex; i++) {
            String query = "./game/" + i + ".html";
            String url = base + "game/" + i + ".html";
            List<Game> list = gameDao.findByUrl(query);
            if (list != null) {
                if (list.size() == 1) {
                    single.submit(new SingleTask(url, list.get(i), gameDao, teamDao));
                } else if (list.size() == 2) {
                    Game game1 = list.get(0);
                    Game game2 = list.get(1);
                    game1.setOpponentTeam(teamDao.findOne(game2.getTeam().getId()));
                    game2.setOpponentTeam(teamDao.findOne(game1.getTeam().getId()));
                   /* gameDao.save(game1);
                    gameDao.save(game2);*/
                }
            }


            if (list == null || list.size() == 0) {
                dou.submit(new DouTask(url, query, teamDao, gameDao));
            }


        }


    }
}
