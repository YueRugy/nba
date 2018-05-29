package com.yue.util;

import com.yue.constant.GamePrimary;
import com.yue.constant.GameSuccess;
import com.yue.dao.TeamDao;
import com.yue.entity.Team;
import org.apache.commons.lang.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by yue on 2018/5/21
 *
 * @see java.util.concurrent.ConcurrentHashMap
 */
public class Reptile implements Callable<String> {
    @Override
    public String call() throws Exception {
        return null;
    }

  /*  private String url;

    private Team team;
    private GameDao gameDao;
    private AtomicInteger count;

    private TeamDao teamDao;

    private PlayerGameDataDao playerGameDataDao;

    // private TeamDao teamDao;

    public Reptile(String url, Team team, GameDao gameDao, AtomicInteger count, TeamDao teamDao, PlayerGameDataDao playerGameDataDao) {
        this.url = url;
        this.team = team;
        this.gameDao = gameDao;
        this.count = count;
        this.teamDao = teamDao;
        this.playerGameDataDao = playerGameDataDao;

        // this.teamDao = teamDao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String call() throws Exception {


        long begin = System.currentTimeMillis();

        count.incrementAndGet();
        CloseableHttpClient httpClient = HttpTranfer.getHttpClient();
        //创建httpget实例
        String content = "ll";
        Document dom = Jsoup.parse(content);

        Element element = dom.getElementsByTag("tbody").first();
        Elements list = element.getElementsByTag("tr");

        int endYear = Integer.parseInt(url.substring(url.lastIndexOf("=") + 1, url.lastIndexOf("#")));
        int before = endYear - 1;
        String gameYear = before + "-" + endYear;

        for (Element e : list) {

            //System.out.println("1111111111111111");
            Game game = new Game();
            Date date = Reptile.strToDate(e.getElementsByClass("date_out").first().text());
            Game data = new Game();
            if (data != null) {
                return "success";
            }
            game.setTime(date);
            String str = e.getElementsByClass("wl").first().text();
            if (str != null) {
                str = str.trim();
                if (str.equals("胜")) {
                    game.setResult(GameSuccess.success.getCode());
                } else {
                    game.setResult(GameSuccess.faild.getCode());
                }
            }
            String str1 = e.getElementsByClass("ha").first().text();
            if (str1 != null) {
                str1 = str1.trim();
                if (str1.equals("主")) {
                    game.setPri(GamePrimary.yes.getCode());
                } else {
                    game.setPri(GamePrimary.no.getCode());
                }

            }
            // System.out.println();
            String str2 = e.getElementsByClass("result_out").first().getElementsByTag("a").first().text();

            //  String flagName = null;

            if (str2 != null) {
                str2 = StringUtil.removeNumber(str2.trim());

                String temp = str2.split("-")[0];
                if ("人".equals(temp)) {
                    temp = "76" + "人";
                }

                Team tmpTeam = null;
                if (tmpTeam == null) {
                    tmpTeam = null;
                    // temp = tmpTeam.getChinaName();

                }

                //  flagName = tmpTeam.getSimple();
                game.setCompetitor(temp);

            }

            String str3 = e.getElementsByClass("fgper").first().text();

            if (str3 != null) {
                str3 = StringUtil.removeSpecifiedChar(str3.trim(), "%");
                game.setShootRate(Double.parseDouble(str3));
            }

            String str4 = e.getElementsByClass("fg").first().text();
            game.setShoot(Integer.parseInt(str4));
            String str5 = e.getElementsByClass("fga").first().text();
            game.setShooting(Integer.parseInt(str5));

            String str6 = e.getElementsByClass("threepper").first().text();

            if (str6 != null) {
                str6 = StringUtil.removeSpecifiedChar(str6.trim(), "%");
                game.setFShootRate(Double.parseDouble(str6));
            }
            String str7 = e.getElementsByClass("threep").first().text();
            game.setFShoot(Integer.parseInt(str7));
            String str8 = e.getElementsByClass("threepa").first().text();
            game.setFShooting(Integer.parseInt(str8));

            String str9 = e.getElementsByClass("ftper").first().text();
            if (str9 != null) {
                str9 = StringUtil.removeSpecifiedChar(str9.trim(), "%");
                game.setPShootRate(Double.parseDouble(str9));
            }
            String str10 = e.getElementsByClass("ft").first().text();
            game.setPShoot(Integer.parseInt(str10));
            String str11 = e.getElementsByClass("fta").first().text();
            game.setPShooting(Integer.parseInt(str11));


            String tr = e.getElementsByClass("trb").first().text();
            game.setRebound(Integer.parseInt(tr));
            String tr1 = e.getElementsByClass("orb").first().text();
            game.setORebound(Integer.parseInt(tr1));
            String tr2 = e.getElementsByClass("drb").first().text();
            game.setBRebound(Integer.parseInt(tr2));
            String tr3 = e.getElementsByClass("ast").first().text();
            game.setAssists(Integer.parseInt(tr3));
            String tr4 = e.getElementsByClass("stl").first().text();
            game.setSteal(Integer.parseInt(tr4));
            String tr5 = e.getElementsByClass("blk").first().text();
            game.setBlock(Integer.parseInt(tr5));


            String tr6 = e.getElementsByClass("tov").first().text();
            game.setMiss(Integer.parseInt(tr6));
            String tr7 = e.getElementsByClass("pf").first().text();
            game.setFoul(Integer.parseInt(tr7));
            String tr8 = e.getElementsByClass("pts").first().text();
            game.setScore(Integer.parseInt(tr8));


            game.setTeam(team);
            game.setGameYear(gameYear);
            game = gameDao.save(game);


            playerDataInit(e, game);


        }

        count.decrementAndGet();
        System.out.println("消耗---" + (System.currentTimeMillis() - begin) + "毫秒");
        return "aaa";
    }

    private void playerDataInit(Element el, Game game) {
        try {
            String link = el.getElementsByClass("result_out").first().getElementsByTag("a").first().attr("href");
            link = link.replaceFirst(".", "http://www.stat-nba.com");
            CloseableHttpClient httpClientLink = HttpTranfer.getHttpClient();

            String info = HttpTranfer.getContent(httpClientLink, link);

            Document dom1 = Jsoup.parse(info);
            String per = "stat_box";
            String priId = per + team.getSimple();

            //  String sId = per + flagName;

            //主队球员信息
            Element priElement = dom1.getElementById(priId);
            // Element secElement = dom1.getElementById(sId);
            Elements priList = priElement.getElementsByTag("tbody").first().getElementsByTag("tr");
            //  Elements secList = secElement.getElementsByTag("tbody").first().getElementsByTag("tr");
            //  priList.addAll(secList);

            save(game, priList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void save(Game game, Elements priList) {
        try {
            for (Element e : priList) {
                PlayerGameData playerGameData = new PlayerGameData();

                String name = e.getElementsByClass("player_name_out").first().getElementsByTag("a").first().text();
                playerGameData.setPlayerName(name);
                playerGameData.setStartIng(Integer.parseInt(e.getElementsByClass("gs").first().text()));
                playerGameData.setTime(Integer.parseInt(e.getElementsByClass("mp").first().text()));

                String str3 = e.getElementsByClass("fgper").first().text();

                if (str3 != null) {
                    str3 = StringUtil.removeSpecifiedChar(str3.trim(), "%");
                    if (StringUtils.isBlank(str3)) {
                        str3 = "0";
                    } else {
                        str3 = StringUtil.removeSpecifiedChar(str3.trim(), "%");
                    }
                    playerGameData.setShootRate(Double.parseDouble(str3));
                }

                String str4 = e.getElementsByClass("fg").first().text();
                playerGameData.setShoot(Integer.parseInt(str4));
                String str5 = e.getElementsByClass("fga").first().text();
                playerGameData.setShooting(Integer.parseInt(str5));

                String str6 = e.getElementsByClass("threepper").first().text();

                if (str6 != null) {
                    str6 = StringUtil.removeSpecifiedChar(str6.trim(), "%");
                    if (StringUtils.isBlank(str6)) {
                        str6 = "0";
                    } else {
                        str6 = StringUtil.removeSpecifiedChar(str6.trim(), "%");
                    }
                    playerGameData.setFShootRate(Double.parseDouble(str6));
                }
                String str7 = e.getElementsByClass("threep").first().text();
                playerGameData.setFShoot(Integer.parseInt(str7));
                String str8 = e.getElementsByClass("threepa").first().text();
                playerGameData.setFShooting(Integer.parseInt(str8));

                String str9 = e.getElementsByClass("ftper").first().text();
                if (str9 != null) {
                    if (StringUtils.isBlank(str9)) {
                        str9 = "0";
                    } else {
                        str9 = StringUtil.removeSpecifiedChar(str9.trim(), "%");
                    }
                    playerGameData.setPShootRate(Double.parseDouble(str9));
                }
                String str10 = e.getElementsByClass("ft").first().text();
                playerGameData.setPShoot(Integer.parseInt(str10));
                String str11 = e.getElementsByClass("fta").first().text();
                playerGameData.setPShooting(Integer.parseInt(str11));


                String tr = e.getElementsByClass("trb").first().text();
                playerGameData.setRebound(Integer.parseInt(tr));
                String tr1 = e.getElementsByClass("orb").first().text();
                playerGameData.setORebound(Integer.parseInt(tr1));
                String tr2 = e.getElementsByClass("drb").first().text();
                playerGameData.setBRebound(Integer.parseInt(tr2));
                String tr3 = e.getElementsByClass("ast").first().text();
                playerGameData.setAssists(Integer.parseInt(tr3));
                String tr4 = e.getElementsByClass("stl").first().text();
                playerGameData.setSteal(Integer.parseInt(tr4));
                String tr5 = e.getElementsByClass("blk").first().text();
                playerGameData.setBlock(Integer.parseInt(tr5));


                String tr6 = e.getElementsByClass("tov").first().text();
                playerGameData.setMiss(Integer.parseInt(tr6));
                String tr7 = e.getElementsByClass("pf").first().text();
                playerGameData.setFoul(Integer.parseInt(tr7));
                String tr8 = e.getElementsByClass("pts").first().text();
                playerGameData.setScore(Integer.parseInt(tr8));

                String tr9 = e.getElementsByClass("ts").first().text();

                if (tr9 != null) {
                    if (StringUtils.isBlank(tr9)) {
                        tr9 = "0";
                    } else {
                        tr9 = StringUtil.removeSpecifiedChar(tr9.trim(), "%");
                    }
                    playerGameData.setRealRate(Double.parseDouble(tr9));
                }

                playerGameData.setGame(game);
                playerGameDataDao.save(playerGameData);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


   */
}
