package com.alioo.monitor.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

@Slf4j
public class HttpUtil {
//    public static final String CHARSET = "UTF-8";

    // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
    //static CloseableHttpClient httpClient = HttpClientBuilder.create().build();


    public static String get(String url, Map<String, String> headers) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        // 创建Get请求
        HttpGet httpGet = new HttpGet(url);

        addHeaders(httpGet, headers);

        // 响应模型
        CloseableHttpResponse response = null;
        String ret = null;
        try {

            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            //log.info("响应状态:" + response.getStatusLine());
            if (responseEntity != null) {
                ret = EntityUtils.toString(responseEntity);
                //log.info("响应长度:" + responseEntity.getContentLength());
                String tmp=ret;
                long length=0;
                if(ret!=null && ret.length()>100){
                    tmp = ret.substring(0, 100);
                    length = ret.length();
                }
                log.info("响应内容length:{},tmp:{}" ,length,tmp);
                return ret;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static void addHeaders(HttpGet httpGet, Map<String, String> headers) {

        if (headers == null || headers.isEmpty()) {
            return;
        }

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            httpGet.addHeader(entry.getKey(), entry.getValue());
        }
    }


}