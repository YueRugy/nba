package com.yue.service;

import com.yue.constant.TeamO;
import com.yue.dao.GameDao;
import com.yue.dao.TeamDao;
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
        final Semaphore semaphore = new Semaphore(20);

        ExecutorService pool = Executors
                .newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (Team t : list) {
            pool.submit(new GameTask(t, gameDao, semaphore));
        }

    }

    public void initOnce() {
        List<Team> list = teamDao.findByOnce(TeamO.once.getValue());
        final Semaphore semaphore = new Semaphore(20);

        ExecutorService pool = Executors
                .newFixedThreadPool(Runtime.getRuntime().availableProcessors());


        for (Team t : list) {
            pool.submit(new GameTask(t, gameDao, semaphore));
        }


    }
}
