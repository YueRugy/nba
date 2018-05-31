package com.yue.service;

import com.yue.constant.TeamO;
import com.yue.dao.GameDao;
import com.yue.dao.TeamDao;
import com.yue.entity.Game;
import com.yue.entity.Team;
import com.yue.task.GameTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by yue on 2018/5/29
 */
@Service
public class GameService {
    private final GameDao gameDao;
    private final TeamDao teamDao;


    @Autowired
    public GameService(GameDao gameDao, TeamDao teamDao) {
        this.gameDao = gameDao;
        this.teamDao = teamDao;
    }

    public void init() {
        List<Team> list = teamDao.findByOnce(TeamO.now.getValue());

        ExecutorService pool = Executors
                .newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (Team t : list) {
            pool.submit(new GameTask(t, gameDao));
        }

    }

    public void initOnce() {
        List<Team> list = teamDao.findByOnce(TeamO.once.getValue());

        ExecutorService pool = Executors
                .newFixedThreadPool(Runtime.getRuntime().availableProcessors());


        for (Team t : list) {
            pool.submit(new GameTask(t, gameDao));
        }


    }

    public void test() throws Exception {

        throw new Exception("aa");
    }

   /* public void refactor() {
        int max = 42426;

        String base = " http://www.stat-nba.com";
        for (int i = 1; i < max; i++) {
            String url = "/game/" + i + ".html";
            String query = "." + url;
            List<Game> list = gameDao.findByUrl(query);
            if (list != null) {
                if (list.size() > 1) {
                    continue;
                }

                if (list.size() == 1) {
                    Game game = list.get(0);
                }
            }

        }

    }*/
}
