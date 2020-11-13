package com.sage.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;

import static com.sage.util.HttpUtil.getParamString;

public class RequestIdAndRid {
    public static void main(String[] args) {
        try {
            String body = code();
            Map<String, String> rid = getRid(body);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static Map<String,String> getRid(String body){
        String requestId = (String) JSONObject.parseObject(body).get("requestId");
        String rid = (String) ((JSONObject) JSONObject.parseObject(body).get("detail")).get("rid");
        HashMap<String, String> map = new HashMap<>();
        map.put(requestId,rid);
        return map;
    }



    public static String code() throws Exception {
        String urlStr = "https://captcha.fengkongcloud.com/ca/v1/register";
        String result = null;
        Map<String, String> map = new HashMap<>();
        map.put("organization", "iqYCUCVctYQ6OmmJwarX");
        map.put("appId", "default");
        map.put("channel", "DEFAULT");
        map.put("lang", "zh-cn");
        map.put("model", "slide");
        map.put("rversion", "1.0.1");
        map.put("sdkver", "1.1.1");
        map.put("data", "%7B%7D");
        map.put("callback", "sm_" + System.currentTimeMillis());
        urlStr = urlStr + "?" + getParamString(map);
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(urlStr);
        get.setHeader("organization", "iqYCUCVctYQ6OmmJwarX");
        get.setHeader("appId", "default");
        get.setHeader("channel", "DEFAULT");
        get.setHeader("lang", "zh-cn");
        get.setHeader("model", "slide");
        get.setHeader("rversion", "1.0.1");
        get.setHeader("sdkver", "1.1.1");
        get.setHeader("data", "%7B%7D");
        get.setHeader("callback", "sm_" + System.currentTimeMillis());
        CloseableHttpResponse response = client.execute(get);
        //获取结果实体
        HttpEntity entity = response.getEntity();
        String body = null;
        if (entity != null) {
            //按指定编码转换结果实体为String类型
            body = EntityUtils.toString(entity, "utf-8");
            body = body.substring(17,body.length()-1);
        }
        EntityUtils.consume(entity);
        //释放链接
        response.close();
        return body;
    }
}
