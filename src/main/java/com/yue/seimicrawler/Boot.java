package com.yue.seimicrawler;

import com.yue.entity.Player;
import com.yue.util.HttpTranfer;
import com.yue.util.StringUtil;
import com.yue.util.TimeUtil;
import org.apache.http.impl.client.CloseableHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * Created by yue on 2018/5/19
 */
public class Boot {

    public static void main(String[] args) throws Exception {
































   /*     String str="1988年9月29日";

        str =      str.replaceAll( "[^x00-xff]" , "-" );
        str=str.substring(0,str.lastIndexOf("-"));
        System.out.println(str);*/

       /* String ing = "2.06米(6尺9寸)";
        ing = ing.substring(0, ing.indexOf("米"));
        System.out.println(ing);

        CloseableHttpClient httpClient = HttpTranfer.getHttpClient();


        String content = HttpTranfer.getContent(httpClient, "http://www.stat-nba.com/player/779.html");

        Document document = Jsoup.parse(content);

        Element element = document.getElementsByClass("playerinfo").first();

        String nameInfo = element.select("div.name").text();
        nameInfo = nameInfo.substring(0, nameInfo.indexOf("百"));
        nameInfo = StringUtil.rightTrim(nameInfo);

        String name = nameInfo.split("/")[0];
        String enName = nameInfo.split("/")[1];

        Player player = new Player();
        player.setName(name);
        player.setSimple(enName);

        Element element1 = element.select("div.detail").first();

        Elements elements = element1.select("div.row");


        for (int i = 0; i < elements.size(); i++) {
            switch (i) {
                case 0:
                    String en = elements.get(i).text();
                    player.setNameEn(en.substring(en.indexOf(":") + 1));
                    break;
                case 1:
                    String pos = elements.get(i).text();
                    player.setPos(pos.substring(pos.indexOf(":") + 1));
                    break;
                case 2:
                    String str = elements.get(i).text();
                    player.setHeight(Double.parseDouble(str.substring(str.indexOf(":") + 1, str.indexOf("米"))));
                    break;
                case 3:
                    String str2 = elements.get(i).text();
                    player.setWeight(Double.parseDouble(str2.substring(str2.indexOf(":") + 1, str2.indexOf("公"))));
                    break;
                case 4:
                    String str1 = elements.get(i).text().replaceAll("[^x00-xff]", "-");
                    str1 = str1.substring(str1.indexOf(":") + 1, str1.lastIndexOf("-"));
                    player.setDate(TimeUtil.strToDate(str1));
                    break;
                default:
                    break;


            }
        }


        System.out.println(player);     */

        //创建httpget实例
/*
*/

/*
*/

    /*    CloseableHttpClient httpClient = HttpTranfer.getHttpClient();*/
    /*    String content = HttpTranfer.getContent(httpClient, "http://www.stat-nba.com/playerList.php?il=A&lil=0");*/
    /*    Document document = Jsoup.parse(content);*/
/*
*/

    /*    Elements elements = document.select("div.playerList");*/
    /*    System.out.println(elements.size());*/
/*
*/

/*
*/

    }

}
