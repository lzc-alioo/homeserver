package com.alioo.monitor.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

@Slf4j
public class HttpUtil {

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


    public static String post(String url, Map<String, String> headers, Map<String, String> datas) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        // 创建Get请求
        HttpPost httpPost = new HttpPost(url);

        addHeaders(httpPost, headers);

        addDatas(httpPost,datas);

        // 响应模型
        CloseableHttpResponse response = null;
        String ret = null;
        try {

            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpPost);
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



    private static void addHeaders(HttpRequestBase request, Map<String, String> headers) {

        if (headers == null || headers.isEmpty()) {
            return;
        }

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            request.addHeader(entry.getKey(), entry.getValue());
        }
    }


    private static void addDatas(HttpPost request, Map<String, String> datas) {

        if (datas == null || datas.isEmpty()) {
            request.setEntity(new StringEntity("", "utf-8"));
            return;
        }



        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : datas.entrySet()) {
            builder.append(entry.getKey() + "=" + entry.getValue() + "&");
        }
        builder.deleteCharAt(builder.length() - 1);

        StringEntity postingString = new StringEntity(builder.toString(), "utf-8");
        request.setEntity(postingString);

    }







}