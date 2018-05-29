package com.yue.task;

import com.yue.dao.GameDao;
import com.yue.entity.Team;
import com.yue.util.Analyze;
import com.yue.util.HttpTranfer;
import com.yue.util.StringUtil;
import org.apache.http.impl.client.CloseableHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.concurrent.Semaphore;

import static com.yue.constant.Url.*;

/**
 * Created by yue on 2018/5/29
 */
public class GameTask implements Runnable {


    private Team team;
    private GameDao gameDao;
    private static final String baseUrl = "http://www.stat-nba.com/team/";
    private Semaphore semaphore;

    public GameTask(Team team, GameDao gameDao, Semaphore semaphore) {
        this.team = team;
        this.gameDao = gameDao;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        String simple = team.getSimple();

        String teamUrl = baseUrl + team.getSimple() + ".html";
        CloseableHttpClient httpClient = HttpTranfer.getHttpClient();
        String info = HttpTranfer.getContent(httpClient, teamUrl);
        Document document = Jsoup.parse(info);
        Element element = document.select("div.superstarList").first();
        Element first = element.select("a.chooser").first();
        Element end = element.select("a.chooserin").first();

        String firstText = StringUtil.removeChinese(first.text());
        String startYear = first.attr("id");
        String endYear = end.attr("id");
        String endStr = endYear.substring(endYear.length() - 4, endYear.length());
        int year = Integer.parseInt(endStr);
        int start = 1985;
        int last = 2017;
        boolean flag = true;
        if (!firstText.equals("常规赛")) {
            String tempStr = startYear.substring(startYear.length() - 4, startYear.length());
            int temp = Integer.parseInt(tempStr);
            start = start > temp ? start : temp;

        } else {
            if (year < start) {
                flag = false;
            }
        }

        if (year < start) {
            flag = false;
        }
        last = last > year ? year : last;

        if (flag) {
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
                        gameDao.save(Analyze.getGame(e, team));
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                } finally {
                    semaphore.release();
                }

            }
        }

    }


}


