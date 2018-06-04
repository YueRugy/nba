package com.yue.test;

import com.yue.util.HttpTranfer;
import org.apache.http.impl.client.CloseableHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * Created by yue on 2018/5/31
 */
public class PlayGameTest {

    public static void main(String[] args) {

        String url = "http://www.stat-nba.com/game/42426.html";
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

        for (Element e : elements) {
            String href=e.select("td.player_name_out").first().getElementsByTag("a").first().attr("href");
            System.out.println(href);
        }












      /*  CloseableHttpClient httpClient = HttpTranfer.getHttpClient();
        String url = "http://www.stat-nba.com/game/42426.html";
        String content = HttpTranfer.getContent(httpClient, url);

        Document document = Jsoup.parse(content);


        Element element = document.select("div.title").first();
        String text = element.text();
        text = text.replaceAll("赛季", ",");


        Elements elements = document.select("div.teamDiv");
        String oneSimeple = elements.get(0).getElementsByTag("a").first().attr("href");
        String twoSimeple = elements.get(1).getElementsByTag("a").first().attr("href");


        String primary = document.select("div.homeaway").first().text();

        System.out.println(document.select("div[style=float: left;margin-top: 25px;margin-left: 10px;font-size: 16px;font-weight: bold;color: #009CFF]").first().text());

        String href = document.select("div.teamDiv").first().getElementsByTag("a").attr("href");*/
    }
}
