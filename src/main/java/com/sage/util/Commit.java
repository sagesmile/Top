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

/**
 * 提交工具类
 */
public class Commit {

    public static int temp = 0;
    public static String send(JSONObject jsonObject) throws Exception{

        ArrayList<String> list = new ArrayList<>();
        list.add("2020111620395774bf2af5cbe3b534d2");
        list.add("202011162040172d8df76f07c2ad34fd");
        list.add("20201116204028a5f8f9442904deb7cd");
        list.add("20201116204044a1829f3625d5c0b473");
        list.add("20201116204054a39dff587f97977f6b");

        String url = "https://wxmall.topsports.com.cn/order/create";
        jsonObject.put("rid",list.get(temp));
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
        httpPost.setHeader("Authorization","324e36fd-c5bc-4460-894f-e1825e9e20fe");
        httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36 MicroMessenger/7.0.9.501 NetType/WIFI MiniProgramEnv/Windows WindowsWechat");
        httpPost.setHeader("Referer", "https://servicewechat.com/wx71a6af1f91734f18/22/page-frame.html");
        httpPost.setHeader("Accept-Encoding","gzip, deflate, br");
        httpPost.setHeader("content-type","application/json");
        httpPost.setHeader("Accept-Language","zh-cn");
        //执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = client.execute(httpPost);
        //获取结果实体
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            //按指定编码转换结果实体为String类型
            body = EntityUtils.toString(entity, "utf-8");
        }
        EntityUtils.consume(entity);
        temp+=1;
        //释放链接
        response.close();
        System.out.println(body);
        return body;
    }



}
