package com.yue.service;

import com.yue.constant.TeamD;
import com.yue.constant.TeamO;
import com.yue.constant.TeamP;
import com.yue.dao.TeamDao;
import com.yue.entity.Team;
import com.yue.util.HttpTranfer;
import org.apache.http.impl.client.CloseableHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Created by yue on 2018/5/19
 */
@Service
public class TeamService {


    private final TeamDao teamDao;

    private final static String url = "http://www.stat-nba.com/teamList.php";

    private static final String baseUrl = "http://www.stat-nba.com/team/";

    @Autowired
    public TeamService(TeamDao teamDao) {
        this.teamDao = teamDao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void init() throws InterruptedException {

        CloseableHttpClient httpClient = HttpTranfer.getHttpClient();
        String content = HttpTranfer.getContent(httpClient, url);


        Document dom = Jsoup.parse(content);

        Element nowTable = dom.getElementsByClass("stat_box").first().getElementsByTag("table").first();


        Element info = nowTable.getElementsByTag("tr").last();
        Elements list = info.getElementsByTag("td");
        TeamP[] teamPS = TeamP.values();

        ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        final Semaphore semaphore = new Semaphore(5);

        for (int i = 0; i < list.size(); i++) {


            final int code = teamPS[i].getCode();


            final Element element = list.get(i);
            //  Thread.sleep(2000);

            final int dCode;
            if (i < 3) {
                dCode = TeamD.east.getCode();
            } else {
                dCode = TeamD.west.getCode();
            }
            Elements es = element.select("div.team");
            for (Element temp : es) {
                pool.submit(() -> {
                    try {
                        semaphore.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Team team = new Team();
                    team.setPart(code);
                    team.setDirection(dCode);
                    Element e = temp.getElementsByTag("a").first();
                    //   System.out.println(e.toString());
                    String name = e.getElementsByTag("div").first().text();
                    if (name == null) {
                        System.out.println();
                    }
                    team.setName(name);
                    String href = e.attr("href");
                    String simple = href.substring(href.lastIndexOf("/") + 1, href.lastIndexOf("."));
                    team.setSimple(simple);
                    team.setOnce(TeamO.now.getValue());

                    CloseableHttpClient httpClientTemp = HttpTranfer.getHttpClient();

                    String contentTemp = HttpTranfer.getContent(httpClientTemp, baseUrl + simple + ".html");

                    Document document = Jsoup.parse(contentTemp);
                    Element element1 = document.getElementsByClass("value").first();
                    String en = element1.text();
                    en = en.substring(0 , en.lastIndexOf("("));

                    team.setNameEn(en);
                    teamDao.save(team);
                    semaphore.release();

                });
            }

        }
        initOld(dom);
        if (pool.awaitTermination(3000, TimeUnit.MILLISECONDS)) {
            pool.shutdown();
        }


    }

    private void initOld(Document dom) throws InterruptedException {
        final Semaphore semaphore = new Semaphore(5);

        Element el=dom.getElementsByClass("teamList").first();

        Elements list = el.select("div.team");


        ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (Element temp : list) {
            // Thread.sleep(2000);
            pool.submit(() -> {
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                /*try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                Team team = new Team();

                team.setOnce(TeamO.once.getValue());

                Element e = temp.getElementsByTag("a").first();
                String name = e.getElementsByTag("div").first().text();
                team.setName(name);
                String href = e.attr("href");
                String simple = href.substring(href.lastIndexOf("/") + 1, href.lastIndexOf("."));
                team.setSimple(simple);

                CloseableHttpClient httpClientTemp = HttpTranfer.getHttpClient();

                String contentTemp = HttpTranfer.getContent(httpClientTemp, baseUrl + simple + ".html");

                Document document = Jsoup.parse(contentTemp);
                Element element = document.getElementsByClass("value").first();
                String en = element.text();
                en = en.substring(0 , en.lastIndexOf("("));

                team.setNameEn(en);
                teamDao.save(team);
                semaphore.release();
            });
        }

        if (pool.awaitTermination(3000, TimeUnit.MILLISECONDS)) {
            pool.shutdown();
        }

    }

    public List<Team> eastTeam() {

        return teamDao.findByDirection(TeamD.east.getCode());
    }

    public List<Team> westTeam() {
        return teamDao.findByDirection(TeamD.west.getCode());
    }

    public List<Team> oldTeam() {
        return teamDao.findByDirectionIsNull();
    }
}
