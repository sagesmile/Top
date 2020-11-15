package com.sage.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;

public class Commit {
    public static String send(JSONObject jsonObject) throws Exception{

        ArrayList<String> list = new ArrayList<>();
        list.add("202011151644562694982f2edcadc2e9");
        list.add("202011151645007bffe07007721d30db");
        list.add("2020111516450332659ffd0fbb849639");
        list.add("2020111516450712a94edb87f043de59");

        String url = "https://wxmall.topsports.com.cn/order/create";
        jsonObject.put("rid",list.get(0));
        String body = "";
        //创建post方式请求对象

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        //装填参数
        StringEntity s = new StringEntity(jsonObject.toString(), "utf-8");
        //设置参数到请求对象中
        httpPost.setEntity(s);
        //设置header信息
        httpPost.setHeader(":Host","wxmall.topsports.com.cn");
        httpPost.setHeader("Connection","keep-alive");
        httpPost.setHeader("Authorization","8bb8417a-f316-42de-a706-6852a4a2f335");
        httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36 MicroMessenger/7.0.9.501 NetType/WIFI MiniProgramEnv/Windows WindowsWechat");
        httpPost.setHeader("Referer", "https://servicewechat.com/wx71a6af1f91734f18/22/page-frame.html");
        httpPost.setHeader("Accept-Encoding","gzip, deflate, br");
        httpPost.setHeader("content-type","application/json");
        httpPost.setHeader("Accept-Language","zh-cn");
        //执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = client.execute(httpPost);
        list.remove(0);
        //获取结果实体
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            //按指定编码转换结果实体为String类型
            body = EntityUtils.toString(entity, "utf-8");
        }
        EntityUtils.consume(entity);
        //释放链接
        response.close();
        System.out.println(body);
        return body;
    }



}
