package com.yue.task;

import com.yue.constant.GamePrimary;
import com.yue.constant.GameSuccess;
import com.yue.dao.GameDao;
import com.yue.dao.TeamDao;
import com.yue.entity.Game;
import com.yue.entity.Team;
import com.yue.util.HttpTranfer;
import org.apache.http.impl.client.CloseableHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static com.yue.util.Analyze.simple;

/**
 * Created by yue on 2018/5/31
 */
public class SingleTask implements Runnable {

    private String url;
    private Game game;
    private GameDao gameDao;
    private TeamDao teamDao;

    public SingleTask(String url, Game game, GameDao gameDao, TeamDao teamDao) {
        this.url = url;
        this.game = game;
        this.gameDao = gameDao;
        this.teamDao = teamDao;

    }

    @Override
    public void run() {

        CloseableHttpClient httpClient = HttpTranfer.getHttpClient();
        String content = HttpTranfer.getContent(httpClient, url);

        Document document = Jsoup.parse(content);
        Team team = teamDao.findOne(game.getTeam().getId());

        Team oTeam = null;
        String st = null;
        Elements elements = document.select("div.teamDiv");
        for (Element el : elements) {
            String href = el.getElementsByTag("a").first().attr("href");
            href = href.substring(href.lastIndexOf("/") + 1, href.lastIndexOf("."));
            if (!team.getSimple().equals(href)) {
                oTeam = teamDao.findBySimple(href);
                st = href;
            }
        }

        game.setOpponentTeam(oTeam);
        gameDao.save(game);

        String baseSelect = "stat_box";

        String selectId = baseSelect + st;
        Element data = document.getElementById(selectId).select("tr.team_all_content").first();

        Game newGame = new Game();
        newGame.setOpponentTeam(team);
        newGame.setGameTime(game.getGameTime());
        newGame.setGameType(game.getGameType());
        newGame.setUrl(game.getUrl());
        newGame.setPri(game.getPri() == GamePrimary.yes.getCode() ?
                GamePrimary.no.getCode() : GamePrimary.yes.getCode());
        newGame.setResult(game.getResult() == GameSuccess.success.getCode() ?
                GameSuccess.faild.getCode() : GameSuccess.success.getCode());

        newGame.setTeam(oTeam);

        simple(data, newGame);

        gameDao.save(newGame);

    }


}
