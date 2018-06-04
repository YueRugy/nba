package com.yue.task;

import com.yue.constant.EmptyUrlEnum;
import com.yue.dao.EmptyUrlDao;
import com.yue.dao.GameDao;
import com.yue.dao.PlayerDao;
import com.yue.dao.TeamDao;
import com.yue.entity.*;
import com.yue.util.HttpTranfer;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.math.BigDecimal;

/**
 * Created by yue on 2018/6/1
 */
public class GPTask implements Runnable {

    private static final String basePlayerUrl = "http://www.stat-nba.com";

    private String url;
    private TeamDao teamDao;
    private PlayerDao playerDao;
    private EmptyUrlDao emptyUrlDao;
    private GameDao gameDao;
    private String query;

    @Override
    public void run() {
        CloseableHttpClient httpClient = HttpTranfer.getHttpClient();
        String content = HttpTranfer.getContent(httpClient, url);

        Document document = Jsoup.parse(content);
        String oneUrl = document.select("div.teamDiv").first().getElementsByTag("a").first().attr("href");
        String twoUrl = document.select("div.teamDiv").last().getElementsByTag("a").first().attr("href");

        String oneSimple = oneUrl.substring(oneUrl.lastIndexOf("/") + 1, oneUrl.lastIndexOf("."));
        String twoSimple = twoUrl.substring(twoUrl.lastIndexOf("/") + 1, twoUrl.lastIndexOf("."));

        String baseId = "stat_box";
        String oneId = baseId + oneSimple;
        Element teamOneElement = document.getElementById(oneId);
        Elements elements = teamOneElement.getElementsByTag("tbody").first().getElementsByTag("tr");


        savePg(oneSimple, elements);
        String twoId = baseId + twoSimple;
        Element teamTwoElement = document.getElementById(twoId);
        Elements elements1 = teamTwoElement.getElementsByTag("tbody").first().getElementsByTag("tr");
        savePg(twoSimple, elements1);


    }

    private void savePg(String simple, Elements elements) {
        for (Element e : elements) {
            String href = e.select("td.player_name_out").first().getElementsByTag("a").first().attr("href");
            href = basePlayerUrl + href;
            Player player = playerDao.findByUrl(href);
            if (player == null) {
                EmptyUrl emptyUrl = new EmptyUrl();
                emptyUrl.setType(EmptyUrlEnum.player.getValue());
                emptyUrl.setUrl(href);
                emptyUrl.setDescription(url);
                emptyUrl.setErrorUrl(url);
                emptyUrlDao.save(emptyUrl);

                continue;
                //createPlayer();
            }
            Team team = teamDao.findBySimple(simple);
            Game game = gameDao.findByUrlAndTId(query, team.getId());

            if (game == null) {
                EmptyUrl emptyUrl = new EmptyUrl();
                emptyUrl.setType(EmptyUrlEnum.game.getValue());
                emptyUrl.setUrl(url);
                emptyUrl.setDescription(simple);
                emptyUrl.setErrorUrl(url);
                emptyUrlDao.save(emptyUrl);
                continue;
            }

            PlayerGame pg = new PlayerGame();
            pg.setPlayer(player);
            pg.setTeam(team);
            pg.setGame(game);

            int fga = Integer.parseInt(e.select("td.fga").first().text());
            pg.setShootNumber(fga);
            int fg = Integer.parseInt(e.select("td.fg").first().text());
            pg.setShoot(fg);
            String fr = e.select("td.fgper").first().text();
            if (StringUtils.isEmpty(fr)) {
                pg.setShootRate(new BigDecimal(0.0));
            } else {
                pg.setShootRate(new BigDecimal(fr));
            }


            int threepa = Integer.parseInt(e.select("td.threepa").first().text());
            pg.setTShootNumber(threepa);
            int threep = Integer.parseInt(e.select("td.threep").first().text());
            pg.setTShoot(threep);
            String threepper = e.select("td.threepper").first().text();
            if (StringUtils.isEmpty(threepper)) {
                pg.setTShootRate(new BigDecimal(0.0));
            } else {
                pg.setTShootRate(new BigDecimal(threepper));
            }

            int fta = Integer.parseInt(e.select("td.fta").first().text());
            pg.setPShootNumber(fta);
            int ft = Integer.parseInt(e.select("td.ft").first().text());
            pg.setPShoot(ft);
            String ftper = e.select("td.ftper").first().text();
            if (StringUtils.isEmpty(ftper)) {
                pg.setPShootRate(new BigDecimal(0.0));
            } else {
                pg.setPShootRate(new BigDecimal(ftper));
            }
            pg.setRebounds(Integer.parseInt(e.select("td.trb").first().text()));
            pg.setORebound(Integer.parseInt(e.select("td.orb").first().text()));
            pg.setTRebound(Integer.parseInt(e.select("td.drb").first().text()));

            pg.setAssists(Integer.parseInt(e.select("td.ast").first().text()));
            pg.setSteal(Integer.parseInt(e.select("td.stl").first().text()));
            pg.setBlock(Integer.parseInt(e.select("td.blk").first().text()));
            pg.setMiss(Integer.parseInt(e.select("td.tov").first().text()));
            pg.setFoul(Integer.parseInt(e.select("td.pf").first().text()));
            pg.setScore(Integer.parseInt(e.select("td.pts").first().text()));

            String ts = e.select("td.ts").first().text();
            if (StringUtils.isEmpty(ts)) {
                pg.setRealRate(new BigDecimal(0.0));
            } else {
                pg.setRealRate(new BigDecimal(ts));
            }


        }
    }


}
