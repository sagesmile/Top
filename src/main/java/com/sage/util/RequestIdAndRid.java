package com.sage.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.net.HttpURLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.sage.util.HttpUtil.getParamString;

/**
 * 多线程测试
 */
public class RequestIdAndRid {


    public static void main(String[] args){


        Fengxiongmao fengxiongmao = new Fengxiongmao();
//        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        ArrayList<String> list = new ArrayList<>();
//        list.add("554725-077");
        list.add("CT0979-602");
//        list.add("555088-105");
//        list.add("575441-105");
//        list.add("DC1788-029");
//        list.add("555088-105");
//        list.add("575441-105");
//        list.add("CT8532-050");
//        list.add("575441-029");
//        list.add("554724-073");
        try {
            while(true){
                for (String code:list){
                    String search = search(code);
                    List<String> idList = commodityIdList(search);
                    for(String id: idList){
                        commodityDetail(id);
                    }
                }
                Thread.sleep(1000);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
//        while (true){
//            try {
//                String detail =fengxiongmao.commodityDetail("900b63140fa847a193185c53fac8e4a1");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

    }



    private static   String search(String keyword) throws Exception{
        String url = "https://wxmall-lv.topsports.com.cn/search/shopCommodity/list";
        HashMap<String, String> map = new HashMap<>();
        map.put("searchKeyword",keyword);
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

    private static void commodityDetail(String id) throws Exception{
        String url = "https://wxmall.topsports.com.cn/shopCommodity/queryShopCommodityDetail/" + id;
        HttpURLConnection conn = HttpUtil.getConn(url, null);
        String result = HttpUtil.get(conn);
        JSONObject sult = JSONObject.parseObject(result);
        JSONObject data = (JSONObject)sult.get("data");
        if ("3".equals(data.get("status").toString())){
            if ((int)data.get("stock") >0){
                String skuList = data.get("skuList").toString();
                List<String> list = JSONArray.parseArray(skuList, String.class);
                HashMap<String, String> map = new HashMap<>();
                for (String one :list){
                    JSONObject jsonObject = JSONObject.parseObject(one);
                    int stock = Integer.parseInt(jsonObject.get("stock").toString());
                    if (stock>0){
                        if (!jsonObject.get("sizeCode").equals(null)){
                            map.put(jsonObject.get("sizeCode").toString(),stock+"双");
                        }else{
                            map.put(jsonObject.get("sizeEur").toString(),stock+"双");
                        }
                    }
                }
                System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) +": " + data.get("shopName").toString()+ "有货售卖："+data.get("productName").toString());
                System.out.println("库存信息："+map);
                System.out.println();
            }
        }
    }

}
