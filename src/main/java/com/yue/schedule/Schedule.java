package com.yue.schedule;

import org.apache.logging.log4j.LogManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by yue on 2018/5/19
 */
@Component
public class Schedule {
    private org.apache.logging.log4j.Logger logger = LogManager.getLogger(Schedule.class);

    //@Scheduled(cron = "0 0/1 * * * ?") //每分钟执行一次
    public void statusCheck() {
        logger.info("每分钟执行一次。开始……");
        //statusTask.healthCheck();
        logger.info("每分钟执行一次。结束。");
    }


    // @Scheduled(fixedRate = 20000)
    public void testTasks() {
        logger.info("每20秒执行一次。开始……");
        //statusTask.healthCheck();
        logger.info("每20秒执行一次。结束。");
    }

    @Scheduled(cron = "0 0/1 * * * ?") //每分钟执行一次
    public void init() throws Exception {
     /*   //创建httpclient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建httpget实例
        HttpGet httpGet = new HttpGet(Url.urlTeam);  //系統有限制
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.110 Safari/537.36");
        //执行http get 请求
        CloseableHttpResponse response;
        response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();//获取返回实体
        //获取每个球队的信息






        //EntityUtils.toString(entity,"utf-8");//获取网页内容，指定编码
        System.out.println("網頁內容" + EntityUtils.toString(entity, "gb2312"));
        //查看响应类型
        System.out.println(entity.getContentType().getValue());
        System.out.println(response.getStatusLine().getStatusCode());
        //HTTP/1.1 200 OK    200
        response.close();
        httpClient.close();*/
    }


}
