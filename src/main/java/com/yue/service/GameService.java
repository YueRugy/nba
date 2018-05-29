package com.yue.service;

import com.yue.constant.*;
import com.yue.dao.GameDao;
import com.yue.dao.TeamDao;
import com.yue.entity.Game;
import com.yue.entity.Team;
import com.yue.util.HttpTranfer;
import com.yue.util.StringUtil;
import com.yue.util.TimeUtil;
import org.apache.http.impl.client.CloseableHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import static com.yue.constant.Url.*;

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
        String baseUrl = "http://www.stat-nba.com/team/";
        final Semaphore semaphore = new Semaphore(20);

        ExecutorService pool = Executors
                .newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (Team t : list) {
            String teamUrl = baseUrl + t.getSimple() + ".html";
            String simple = t.getSimple();
            pool.submit(() -> {
                CloseableHttpClient httpClient = HttpTranfer.getHttpClient();

                String info = HttpTranfer.getContent(httpClient, teamUrl);
                Document document = Jsoup.parse(info);
                Element element = document.select("div.superstarList").first();
                Element first = element.select("a.chooser").first();
                Element end = element.select("a.chooserin").first();

                String firstText = StringUtil.removeChinese(first.text());
                String startYear = first.attr("id");

                int start = 1985;
                int last = 2017;

                if (!firstText.equals("常规赛")) {
                    String tempStr = startYear.substring(startYear.length() - 4, startYear.length());
                    int temp = Integer.parseInt(tempStr);
                    start = start > temp ? start : temp;
                }

                String endYear = end.attr("id");
                String tempStr = endYear.substring(endYear.length() - 4, endYear.length());
                int temp = Integer.parseInt(tempStr);
                last = last > temp ? last : temp;


                for (int i = start; i <= last; i++) {
                    try {
                        semaphore.acquire();
                        int urlEnd = i + 1;
                        String realUrl = url1 + simple + url2 + i + url3 + urlEnd + url4;
                        CloseableHttpClient httpClient1 = HttpTranfer.getHttpClient();
                        String content = HttpTranfer.getContent(httpClient1, realUrl);
                        Document dom = Jsoup.parse(content);
                        Element element1 = dom.getElementsByTag("tbody").first();
                        Elements elements = element1.getElementsByTag("tr");
                        for (Element e : elements) {
                            Game game = new Game();
                            game.setGameType(GameType.season.getValue());
                            game.setTeam(t);

                            String dateStr = e.select("td.date_out").first().text();
                            game.setDate(TimeUtil.strToDate(dateStr));
                            String resultStr = e.select("td.wl").first().text();
                            if ("胜".equals(resultStr)) {
                                game.setResult(GameSuccess.success.getCode());
                            } else if ("负".equals(resultStr)) {
                                game.setResult(GameSuccess.faild.getCode());
                            }

                            String priStr = e.select("td.ha").first().text();

                            if ("客".equals(priStr)) {
                                game.setPri(GamePrimary.no.getCode());
                            } else if ("主".equals(priStr)) {
                                game.setPri(GamePrimary.yes.getCode());
                            }
                            game.setUrl(e.select("td.result_out").first().getElementsByTag("a").first().attr("href"));

                            int fga = Integer.parseInt(e.select("td.fga").first().text());
                            game.setShootNumber(fga);
                            int fg = Integer.parseInt(e.select("td.fg").first().text());
                            game.setShoot(fg);
                            if (fga > 0) {
                                BigDecimal fgb = new BigDecimal(fg);
                                BigDecimal fgab = new BigDecimal(fga);
                                fgb = fgb.divide(fgab, 10, BigDecimal.ROUND_HALF_UP);

                                game.setShootRate(fgb);
                            } else {
                                game.setShootRate(new BigDecimal(0));
                            }

                            int threepa = Integer.parseInt(e.select("td.threepa").first().text());
                            game.setTShootNumber(threepa);
                            int threep = Integer.parseInt(e.select("td.threep").first().text());
                            game.setTShoot(threep);

                            if (threepa > 0) {
                                BigDecimal tb = new BigDecimal(threep);
                                BigDecimal tab = new BigDecimal(threepa);
                                tb = tb.divide(tab, 10, BigDecimal.ROUND_HALF_UP);
                                game.setTShootRate(tb);
                            } else {
                                game.setTShootRate(new BigDecimal(0));
                            }


                            int fta = Integer.parseInt(e.select("td.fta").first().text());
                            game.setPShootNumber(fga);
                            int ft = Integer.parseInt(e.select("td.ft").first().text());
                            game.setPShoot(fg);

                            if (fta > 0) {
                                BigDecimal bigDecimal = new BigDecimal(ft);
                                BigDecimal bigDecimal1 = new BigDecimal(fta);
                                bigDecimal = bigDecimal.divide(bigDecimal1, 10, BigDecimal.ROUND_HALF_UP);
                                game.setPShootRate(bigDecimal);
                            } else {
                                game.setPShootRate(new BigDecimal(0));
                            }

                            game.setRebounds(Integer.parseInt(e.select("td.trb").first().text()));
                            game.setORebound(Integer.parseInt(e.select("td.orb").first().text()));
                            game.setTRebound(Integer.parseInt(e.select("td.drb").first().text()));

                            game.setAssists(Integer.parseInt(e.select("td.ast").first().text()));
                            game.setSteal(Integer.parseInt(e.select("td.stl").first().text()));
                            game.setBlock(Integer.parseInt(e.select("td.blk").first().text()));
                            game.setMiss(Integer.parseInt(e.select("td.tov").first().text()));
                            game.setFoul(Integer.parseInt(e.select("td.pf").first().text()));
                            game.setScore(Integer.parseInt(e.select("td.pts").first().text()));
                            gameDao.save(game);

                        }


                    } catch (Exception e) {
                        e.printStackTrace();

                    } finally {
                        semaphore.release();
                    }

                }
            });
        }

    }
}
