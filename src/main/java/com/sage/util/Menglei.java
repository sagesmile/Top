package com.sage.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Menglei {

    private static int temp = 0;

    private static String challenge[] = {
            "d07b4914cad55c0fd9cdc7845912d774",
            "813c759f26d6e6b4710f29911834e879",
            "619e00514d41fb9ba3165573c07c752a",
    };

    private static String validate[] = {
            "cec6fee7710f9b63f282b7c3e1a54265",
            "7ca39a7a6a0d051fed804ea4a9a62685",
            "109e96f5b73072a8b558db1c4696d232",
    };




    public static void main(String[] args){

        Menglei sage = new Menglei();


//        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
//        ArrayList<String> list = new ArrayList<>();
//        list.add("555088-105");摩卡
//        list.add("DC2198-100");
//        list.add("CT8532-050");
//        list.add("575441-029");
//        list.add("CT8532-050");
        try {
            while(temp<4){
                    String search = sage.search("BQ6472-202");
                    List<String> idList = sage.commodityIdList(search);
                    for(String id: idList){
                        sage.commodityDetail(id);
                    }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
//        while (true){
//            try {
//                String detail =sage.commodityDetail("36a468cb0309489b8bee34cb696fa54e");
////                System.out.println(detail);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

    }



    private  String search(String keyword) throws Exception{
        String url = "https://wxmall.topsports.com.cn/search/shopCommodity/list";
        HashMap<String, String> map = new HashMap<>();
        map.put("searchKeyword",keyword);
        map.put("current","1");
        map.put("pageSize","20");
        map.put("sortColumn","");
        map.put("sortType","asc");
        map.put("filterIds","");
        map.put("shopNo","NKXM45");//NKND20
        HttpURLConnection conn = HttpUtil.getConn(url, map);
        String result = HttpUtil.get(conn);
        return result;
    }

    private  List<String> commodityIdList(String searchResult){
        ArrayList<String> idList = new ArrayList<>();
        List list = (List<Object>)((JSONObject)((JSONObject)JSONObject.parseObject(searchResult).get("data")).get("spu")).get("list");
        for (int i = 0; i < list.size(); i++) {
            JSONObject o = (JSONObject)list.get(i);
            String id = o.get("id").toString();
            idList.add(id);
        }
        return idList;
    }

    private  String commodityDetail(String id) throws Exception{
        int j = 1;
        String url = "https://wxmall.topsports.com.cn/shopCommodity/queryShopCommodityDetail/" + id;
        HttpURLConnection conn = HttpUtil.getConn(url, null);
        String result = HttpUtil.get(conn);
        JSONObject sult = JSONObject.parseObject(result);
        JSONObject data = (JSONObject)sult.get("data");
        if ("3".equals(data.get("status").toString())){
            if ((int)data.get("stock") >0){
                JSONArray skuList = (JSONArray) data.get("skuList");
                for (int i = 0; i < skuList.size(); i++) {
//                for (int i = skuList.size(); i > 0; i--) {
//                    JSONObject info = (JSONObject)skuList.get(i-1);
                    JSONObject info = (JSONObject)skuList.get(i);
                    if ((int)info.get("stock") >0){
                        System.out.println("有货售卖："+id);
                        System.out.println(sult);
                        JSONObject jsonObject = JSONObject.parseObject(param);
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
                        jsonObject.put("validate",validate[temp]);
                        jsonObject.put("seccode",validate[temp]+"|jordan");
                        jsonObject.put("challenge",challenge[temp]);
                        send(jsonObject);
                    }
                }
            }
        }
        return result;
    }




    public String send(JSONObject jsonObject) throws Exception{


        String url = "https://wxmall.topsports.com.cn/order/create";
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
        httpPost.setHeader("Authorization","cb096ce3-f76f-4679-97e9-a3cf6909ead8");
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
        //释放链接
        response.close();
        temp++;
        System.out.println(body);
        return body;
    }



    private static String param = "{\n" +
            "    \"merchantNo\":\"TS\",\n" +
            "    \"rid\":\"202011151614220b6ed9f5bea80eefe3\",\n" +
            "    \"shippingId\":\"8a7a08b57651ae6a01765cb5e70f7b1c\",\n" +
            "    \"subOrderList\":[\n" +
            "        {\n" +
            "            \"shopNo\":\"NKWA02\",\n" +
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
            "                    \"shoppingcartId\":\"2e6b2dc608de488a8170fb35fd87194b\",\n" +
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
            "    ],\n" +
            "    \"verificationType\":2,\n" +
            "    \"validate\":\"360867c08bce2ff4978e57f78bc48a49\",\n" +
            "    \"seccode\":\"360867c08bce2ff4978e57f78bc48a49|jordan\",\n" +
            "    \"challenge\":\"9fa932aaaf1b535db0d231583f39229b\"\n" +
            "}";

}
