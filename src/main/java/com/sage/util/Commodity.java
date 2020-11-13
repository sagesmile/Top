package com.sage.util;

import com.alibaba.fastjson.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Commodity {
    public static void main(String[] args) throws Exception{
        String search = search();
        List<String> list = commodityIdList(search);
        for (int i = 0; i < list.size(); i++) {
            String commodityDetail = commodityDetail(list.get(i));
            System.out.println(commodityDetail);
        }
    }

    private static String url = "https://wxmall.topsports.com.cn/search/shopCommodity/list";

    private static String search() throws Exception{
        String url = "https://wxmall.topsports.com.cn/search/shopCommodity/list";
        HashMap<String, String> map = new HashMap<>();
        map.put("searchKeyword","554724-073");
        map.put("current","1");
        map.put("pageSize","20");
        map.put("sortColumn","");
        map.put("sortType","asc");
        map.put("filterIds","");
        map.put("shopNo","");
        HttpURLConnection conn = HttpUtil.getConn(url, map);
        String result = HttpUtil.get(conn);
        return result;
    }

    private static List<String> commodityIdList(String searchResult){
        ArrayList<String> idList = new ArrayList<>();
        List list = (List<Object>)((JSONObject)((JSONObject)JSONObject.parseObject(searchResult).get("data")).get("spu")).get("list");
        for (int i = 0; i < list.size(); i++) {
            JSONObject o = (JSONObject)list.get(i);
            String id = o.get("id").toString();
            idList.add(id);
        }
        return idList;
    }

    private static String commodityDetail(String id) throws Exception{
        String url = "https://wxmall.topsports.com.cn/shopCommodity/queryShopCommodityDetail/" + id;
        HttpURLConnection conn = HttpUtil.getConn(url, null);
        conn.setRequestProperty("Authorization","Bearer 275aedf9-f807-43c7-8c10-51de7b866c2e");
        String result = HttpUtil.get(conn);
        return result;
    }
}
