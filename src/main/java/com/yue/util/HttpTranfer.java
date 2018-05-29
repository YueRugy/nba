package com.yue.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.*;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by yue on 2018/5/23
 */
public class HttpTranfer {
    /* 连接池最大生成连接数200 */
    private static int pool_MaxTotal = 200;
    /* 连接池默认路由最大连接数,默认为20 */
    private static int pool_MaxRoute = 20;

    private static int request_TimeOut = 60000;

    //单例模式创建连接池
    private static class HttpPoolManagerSingle {
        private static final PoolingHttpClientConnectionManager httpPoolManager =
                new PoolingHttpClientConnectionManager();

        static {
            httpPoolManager.setMaxTotal(pool_MaxTotal);
            httpPoolManager.setDefaultMaxPerRoute(pool_MaxRoute);
        }

    }


    public static CloseableHttpClient getHttpClient() {
        CookieStore cookieStore = new BasicCookieStore();

        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();

        RequestConfig defaultRequestConfig = RequestConfig
                .custom()
                .setConnectTimeout(request_TimeOut)
                .setConnectionRequestTimeout(request_TimeOut)
                .setSocketTimeout(request_TimeOut)
                .setCookieSpec(CookieSpecs.DEFAULT)
                .setExpectContinueEnabled(true)
                .setTargetPreferredAuthSchemes(
                        Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
                .build();

        // 设置重定向策略
        LaxRedirectStrategy redirectStrategy = new LaxRedirectStrategy();

        // 创建httpClient
        return HttpClients.custom()
                .setConnectionManager(HttpPoolManagerSingle.httpPoolManager)
                .setConnectionManagerShared(true)
                .setDefaultCookieStore(cookieStore)
                .setDefaultCredentialsProvider(credentialsProvider)
                .setDefaultRequestConfig(defaultRequestConfig)
                .setRedirectStrategy(redirectStrategy)
                .build();
    }


    public static String getContent(CloseableHttpClient httpClient, String url) {
        CloseableHttpResponse response = null;
        try {
            HttpGet httpGet = new HttpGet(url);  //系統有限制
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.110 Safari/537.36");
            //执行http get 请求

            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();//获取返回实体
            return EntityUtils.toString(entity, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
         /* 关闭响应实体 */
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        /* 关闭httpClient连接 */
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return null;
    }
}
