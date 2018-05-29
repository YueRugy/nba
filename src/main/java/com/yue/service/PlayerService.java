package com.yue.service;

import com.yue.constant.Common;
import com.yue.dao.PlayerDao;
import com.yue.entity.Player;
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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by yue on 2018/5/28
 */
@Service
public class PlayerService {
    private final PlayerDao playerDao;

    @Autowired
    public PlayerService(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    public void init() {

        final AtomicInteger errorNumber = new AtomicInteger(0);

        final String base = "http://www.stat-nba.com/playerList.php?il=";

        for (String str : Common.uppercase) {
            String realUrl = base + str + "&lil=0";

            CloseableHttpClient httpClient = HttpTranfer.getHttpClient();
            String content = HttpTranfer.getContent(httpClient, realUrl);


            Document document = Jsoup.parse(content);

            Element element = document.select("div.playerList").last();
            Elements elements = element.select("div.name");

            final Semaphore semaphore = new Semaphore(10);

            ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

            String baseUrl = "http://www.stat-nba.com";
            for (Element temp : elements) {
                String tempUrl = temp.getElementsByTag("a").first().attr("href");
                tempUrl = tempUrl.substring(1, tempUrl.length());
                final String playerUrl = baseUrl + tempUrl;

                pool.submit(() -> {
                    try {
                        semaphore.acquire();

                        CloseableHttpClient httpClient1 = HttpTranfer.getHttpClient();


                        String content1 = HttpTranfer.getContent(httpClient1, playerUrl);
                        Document document1 = Jsoup.parse(content1);
                        Element element2 = document1.getElementsByClass("playerinfo").first();

                        String nameInfo = element2.select("div.name").text();
                        if (nameInfo.indexOf("百") > 0) {
                            nameInfo = nameInfo.substring(0, nameInfo.indexOf("百"));
                        } else {
                            nameInfo = nameInfo.substring(0, nameInfo.lastIndexOf("w"));
                        }
                        nameInfo = StringUtil.rightTrim(nameInfo);

                        String name = nameInfo.split("/")[0];
                        String enName = nameInfo.split("/")[1];

                        Player player = new Player();
                        player.setName(name);
                        player.setSimple(enName);
                        player.setUrl(playerUrl);

                        Element element1 = element2.select("div.detail").first();
                        Elements elementList = element1.select("div.row");


                        for (int i = 0; i < elementList.size(); i++) {
                            switch (i) {
                                case 0:
                                    String en = elementList.get(i).text();
                                    player.setNameEn(en.substring(en.indexOf(":") + 1));
                                    break;
                                case 1:
                                    String pos = elementList.get(i).text();
                                    player.setPos(pos.substring(pos.indexOf(":") + 1));
                                    break;
                                case 2:
                                    String str11 = elementList.get(i).text();
                                    player.setHeight(Double.parseDouble(str11.substring(str11.indexOf(":") + 1, str11.indexOf("米"))));
                                    break;
                                case 3:
                                    String str2 = elementList.get(i).text();
                                    player.setWeight(Double.parseDouble(str2.substring(str2.indexOf(":") + 1, str2.indexOf("公"))));
                                    break;
                                case 4:
                                    String str1 = elementList.get(i).text().replaceAll("[^x00-xff]", "-");
                                    str1 = str1.substring(str1.indexOf(":") + 1, str1.lastIndexOf("-"));
                                    player.setDate(TimeUtil.strToDate(str1));
                                    break;
                                default:
                                    break;
                            }
                        }

                        playerDao.save(player);
                    } catch (InterruptedException e) {
                        errorNumber.addAndGet(1);
                        if (errorNumber.get() != 0) {
                            System.out.println(errorNumber);
                        }
                        e.printStackTrace();
                    } finally {
                        semaphore.release();
                    }

                });
            }


        }


    }
}
