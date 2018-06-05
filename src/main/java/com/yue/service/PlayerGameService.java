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
import java.util.concurrent.TimeUnit;

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

    public void refactor() throws InterruptedException {
        /*int endIndex = 42426;
        int startIndex = 1;

        String base = "http://www.stat-nba.com/";

        ExecutorService single = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() / 2 + 1);
        ExecutorService dou = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() / 2 + 1);

        for (int i = startIndex; i <= endIndex; i++) {
            String query = "./game/" + i + ".html";
            String url = base + "game/" + i + ".html";
            List<Game> list = gameDao.findByUrl(query);
            if (list != null && list.size() == 1) {
                if (list.size() == 1) {
                    single.submit(new SingleTask(url, list.get(i), gameDao, teamDao));
                }
            }

            if (list == null || list.size() == 0) {
                dou.submit(new DouTask(url, query, teamDao, gameDao));
            }


        }*/


        int count = gameDao.getCount();

        System.out.println(count);
        List<Game> list = gameDao.findOneUrl();
       /* list = list.subList(0, 1);

        Game game=gameDao.findOne(80361);
        game.setOpponentTeam(teamDao.findOne(530));
        gameDao.save(game);*/


        String base = "http://www.stat-nba.com/";
        ExecutorService single = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
        for (Game game : list) {
            String url = base + game.getUrl().substring(1, game.getUrl().length());
            single.submit(new SingleTask(url, game, gameDao, teamDao));

            if (single.awaitTermination(3000, TimeUnit.MILLISECONDS)) {
                single.shutdown();
            }
        }


    }

    public void updateO() {
        List<Game> list = gameDao.selectO();
        for (int i = 0; i < list.size(); ) {

            list.get(i).setOpponentTeam(list.get(i + 1).getTeam());
            list.get(i + 1).setOpponentTeam(list.get(i).getTeam());
            i += 2;
        }
        gameDao.save(list);


    }

    public void create() {
        int endIndex = 42426;
        int startIndex = 1;

        String base = "http://www.stat-nba.com/";

        ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);

        for (int i = startIndex; i <= endIndex; i++) {
            String url = base + "game/" + i + ".html";
            String query = "./game/" + i + ".html";
            //pool.submit()

        }


    }
}
