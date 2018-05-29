package com.yue.seimicrawler;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.xpath.model.JXDocument;
import com.yue.constant.Url;
import com.yue.util.HttpTranfer;
import com.yue.util.StringUtil;
import org.apache.http.impl.client.CloseableHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.yue.constant.Url.*;

/**
 * Created by yue on 2018/5/19
 */

public class Basic extends BaseSeimiCrawler {
    @Override
    public String[] startUrls() {
        return new String[]{"http://www.cnblogs.com/", "http://www.cnblogs.com/"};
    }

    @Override
    public void start(Response response) {
        JXDocument doc = response.document();
        try {
            List<Object> urls = doc.sel("//a[@class='right_more']/@href");
            logger.info("{}", urls.size());
            Map map = new HashMap();
            map.put("ddd", "aaa");
            for (Object s : urls) {
                push(new Request(s.toString(), "getTitle").setParams(map));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        final String url = "http://www.stat-nba.com/team/ATL.html";


        CloseableHttpClient httpClient = HttpTranfer.getHttpClient();

        String info = HttpTranfer.getContent(httpClient, url);


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
            int urlEnd=i+1;
            String realUrl=url1+"ATL"+url2+i+url3+urlEnd+url4;


            System.out.println(realUrl);
        }


    }


}
