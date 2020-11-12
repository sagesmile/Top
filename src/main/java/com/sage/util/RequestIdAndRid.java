package com.sage.util;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

public class RequestIdAndRid {

    private static String url = "/ca/v1/register?organization=iqYCUCVctYQ6OmmJwarX&appId=default&channel=DEFAULT&lang=zh-cn&model=slide&rversion=1.0.1&sdkver=1.1.1&data=%7B%7D&callback=sm_"+System.currentTimeMillis()+" HTTP/1.1";

    private static HttpURLConnection conn= null;

    public static void main(String[] args) {
        try {
            getRid();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<String,String> getRid() throws Exception{
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("organization","iqYCUCVctYQ6OmmJwarX");
            map.put("appId","default");
            map.put("channel","DEFAULT");
            map.put("lang","zh-cn");
            map.put("model","slide");
            map.put("rversion","1.0.1");
            map.put("sdkver","1.1.1");
            map.put("data","%7B%7D");
            map.put("callback","sm_"+System.currentTimeMillis());
        HttpURLConnection conn = HttpUtil.getConn(url,map, RequestIdAndRid.conn);
        conn.setRequestProperty("Host","captcha.fengkongcloud.com");
        conn.setRequestProperty("Referer","https://servicewechat.com/wx71a6af1f91734f18/21/page-frame.html");
        String result = HttpUtil.get(conn);
        System.out.println(result);
        return null;
    }
}
