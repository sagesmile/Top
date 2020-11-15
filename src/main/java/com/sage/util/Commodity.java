package com.sage.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


import java.net.HttpURLConnection;
import java.util.*;

import static com.sage.util.HttpUtil.getParamString;

public class Commodity {
    public static void main(String[] args){
        try {
            while(true){
                String search = search("555112-103");
                List<String> idList = commodityIdList(search);
                for(String id: idList){
                    commodityDetail(id);
                }
                Thread.sleep(1000);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    private static String search(String keyword) throws Exception{
        String url = "https://wxmall.topsports.com.cn/search/shopCommodity/list";
        HashMap<String, String> map = new HashMap<>();
        map.put("searchKeyword","keyword");
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
        String result = HttpUtil.get(conn);
        JSONObject sult = JSONObject.parseObject(result);
        JSONObject data = (JSONObject)sult.get("data");
        if ("3".equals(data.get("status").toString())){
            if ((int)data.get("stock") >0){
                JSONArray skuList = (JSONArray) data.get("skuList");
                for (int i = 0; i < skuList.size(); i++) {
                    JSONObject info = (JSONObject)skuList.get(i);
                    if ((int)info.get("stock") >0){
                        System.out.println("有货售卖："+id);
                        JSONObject jsonObject = JSONObject.parseObject(param);
                        jsonObject.put("rid","202011142240537246bafb0cdda8a37b");
                        JSONArray array = jsonObject.getJSONArray("subOrderList");
                        JSONObject subOrderList = (JSONObject) array.get(0);

                        subOrderList.put("shopNo",data.get("shopNo").toString());//设置店铺号
                        JSONArray arrayOne = subOrderList.getJSONArray("commodityList");
                        JSONObject commodityList = (JSONObject) arrayOne.get(0);
                        commodityList.put("productCode",data.get("productCode").toString());
                        commodityList.put("productNo",data.get("productNo").toString());
                        commodityList.put("sizeNo",info.get("sizeNo").toString());
                        commodityList.put("sizeCode",info.get("sizeCode").toString());
                        commodityList.put("brandDetailNo",data.get("brandDetailNo").toString());
                        commodityList.put("skuId",info.get("id").toString());
                        commodityList.put("skuNo",info.get("skuNo").toString());
                        commodityList.put("shopCommodityId",data.get("id").toString());
                        arrayOne.set(0,commodityList);
                        subOrderList.put("commodityList",arrayOne);
                        array.set(0,subOrderList);
                        jsonObject.put("subOrderList",array);
                        Commit.send(jsonObject);
                        break;
                    }
                }
            }
        }
        return result;

    }

    private static String param = "{\n" +
            "    \"merchantNo\":\"TS\",\n" +
            "    \"rid\":\"202011151614220b6ed9f5bea80eefe3\",\n" +
            "    \"shippingId\":\"8a7a099774d3ca74017520de7a8a7a7d\",\n" +
            "    \"subOrderList\":[\n" +
            "        {\n" +
            "            \"shopNo\":\"NKCD94\",\n" +
            "            \"totalNum\":1,\n" +
            "            \"totalPrice\":null,\n" +
            "            \"virtualShopFlag\":0,\n" +
            "            \"expressType\":2,\n" +
            "            \"remark\":null,\n" +
            "            \"fullDiscountAmount\":null,\n" +
            "            \"fullReductionAmount\":null,\n" +
            "            \"couponAmount\":\"0.00\",\n" +
            "            \"commodityList\":[\n" +
            "                {\n" +
            "                    \"map\":{\n" +
            "\n" +
            "                    },\n" +
            "                    \"orderByClause\":null,\n" +
            "                    \"shoppingcartId\":\"b46e69968ae0461da85ce1591ac7acbc\",\n" +
            "                    \"paterId\":null,\n" +
            "                    \"productCode\":\"CT0978-600\",\n" +
            "                    \"productNo\":\"20200708000342\",\n" +
            "                    \"colorNo\":\"00\",\n" +
            "                    \"sizeNo\":\"20160426000047\",\n" +
            "                    \"sizeCode\":\"9.5\",\n" +
            "                    \"brandDetailNo\":\"NK01\",\n" +
            "                    \"proNo\":null,\n" +
            "                    \"proName\":null,\n" +
            "                    \"assignProNo\":\"0\",\n" +
            "                    \"skuId\":\"d99a13d830d44fe29ce786e734288069\",\n" +
            "                    \"skuNo\":\"20200826003155\",\n" +
            "                    \"shopCommodityId\":\"381a5d6c4a604ae8b9982bb952db7a81\",\n" +
            "                    \"num\":1,\n" +
            "                    \"status\":3,\n" +
            "                    \"itemFlag\":0,\n" +
            "                    \"usedTicket\":null,\n" +
            "                    \"activityType\":0,\n" +
            "                    \"activityTypeStr\":null,\n" +
            "                    \"usedTickets\":null,\n" +
            "                    \"liveType\":0,\n" +
            "                    \"roomId\":null,\n" +
            "                    \"roomName\":\"\",\n" +
            "                    \"live_type\":0,\n" +
            "                    \"room_id\":\"\",\n" +
            "                    \"room_name\":\"\"\n" +
            "                }\n" +
            "            ],\n" +
            "            \"ticketCodes\":null,\n" +
            "            \"vipPrefAmount\":\"0.00\",\n" +
            "            \"prefAmount\":\"0.00\",\n" +
            "            \"orderTickets\":null,\n" +
            "            \"ticketPresentDtos\":null,\n" +
            "            \"expressAmount\":\"0.00\",\n" +
            "            \"cashOnDelivery\":0,\n" +
            "            \"promotionAmount\":\"0.00\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"purchaseType\":2,\n" +
            "    \"usedPlatformCouponList\":[\n" +
            "\n" +
            "    ]\n" +
            "}";
}
