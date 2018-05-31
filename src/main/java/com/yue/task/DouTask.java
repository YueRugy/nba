package com.yue.task;

import com.yue.constant.GamePrimary;
import com.yue.constant.GameSuccess;
import com.yue.constant.GameType;
import com.yue.dao.GameDao;
import com.yue.dao.TeamDao;
import com.yue.entity.Game;
import com.yue.util.Analyze;
import com.yue.util.HttpTranfer;
import com.yue.util.TimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Date;

/**
 * Created by yue on 2018/5/31
 */
public class DouTask implements Runnable {

    private String url;
    private String query;

    private TeamDao teamDao;

    private GameDao gameDao;


    public DouTask(String url, String query, TeamDao teamDao, GameDao gameDao) {
        this.url = url;
        this.query = query;
        this.teamDao = teamDao;
        this.gameDao = gameDao;
    }

    @Override
    public void run() {
        CloseableHttpClient httpClient = HttpTranfer.getHttpClient();
        String content = HttpTranfer.getContent(httpClient, url);

        Document document = Jsoup.parse(content);
        Element element = document.select("div.title").first();
        String text = element.text();
        text = text.replaceAll("赛季", ",");
        String[] strs = text.split(",");
        String season = StringUtils.trim(strs[0]);
        String typeStr = StringUtils.trim(strs[1]);

        GameType gameType = typeStr.equals("季后赛") ? GameType.playoff : GameType.season;

        Date time = TimeUtil.strToDate(document.select("div[style=float: left;margin-top: 25px;margin-left: 10px;font-size: 16px;font-weight: bold;color: #009CFF]").first().text());

        String oneUrl = document.select("div.teamDiv").first().getElementsByTag("a").first().attr("href");
        String twoUrl = document.select("div.teamDiv").last().getElementsByTag("a").first().attr("href");


        String oneSimple = oneUrl.substring(oneUrl.lastIndexOf("/") + 1, oneUrl.lastIndexOf("."));
        String twoSimple = twoUrl.substring(twoUrl.lastIndexOf("/") + 1, twoUrl.lastIndexOf("."));

        Game oneGame = new Game();
        oneGame.setGameTime(season);
        oneGame.setGameType(gameType.getValue());
        oneGame.setUrl(query);
        oneGame.setDate(time);
        oneGame.setPri(GamePrimary.no.getCode());
        oneGame.setTeam(teamDao.findBySimple(oneSimple));
        oneGame.setOpponentTeam(teamDao.findBySimple(twoSimple));

        Game twoGame = new Game();
        twoGame.setGameTime(season);
        twoGame.setGameType(gameType.getValue());
        twoGame.setUrl(query);
        twoGame.setDate(time);
        twoGame.setPri(GamePrimary.yes.getCode());
        twoGame.setTeam(teamDao.findBySimple(twoSimple));
        twoGame.setOpponentTeam(teamDao.findBySimple(oneSimple));

        String baseSelect = "stat_box";

        String oneId = baseSelect + oneSimple;
        Element data1 = document.getElementById(oneId).select("tr.team_all_content").first();
        Analyze.simple(data1, oneGame);

        String twoId = baseSelect + twoSimple;
        Element data2 = document.getElementById(twoId).select("tr.team_all_content").first();
        Analyze.simple(data2, twoGame);

        if (oneGame.getScore() > twoGame.getScore()) {
            oneGame.setResult(GameSuccess.success.getCode());
            twoGame.setResult(GameSuccess.faild.getCode());
        } else {
            twoGame.setResult(GameSuccess.success.getCode());
            oneGame.setResult(GameSuccess.faild.getCode());
        }


      /*  gameDao.save(oneGame);
        gameDao.save(twoGame);*/
        //  String primary = document.select("div.homeaway").first().text();


    }
}
