package com.sage.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Menglei {

    private static int temp = 0;

    private static String token = "ad057a53-aacd-4f17-a45a-7ed5d76d9a9a";

    private static String challenge[] = {
            "f3ac588d789e291c0f83edfbbbf5d881",
    };

    private static String validate[] = {
            "a4337e26e221e15137e0ff5c7cfd1a3f",
    };

    private static String addCartUrl = "https://wxmall-lv.topsports.com.cn/shoppingcart";
    private static String getCartUrl = "https://wxmall-lv.topsports.com.cn/shoppingcart/index";
    private static String commodityUrl = "https://wxmall-lv.topsports.com.cn/shopCommodity/queryShopCommodityDetail/";
    private static String createOrder = "https://wxmall-lv.topsports.com.cn/order/create";



    public static void main(String[] args){

        Menglei sage = new Menglei();


//        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
//        ArrayList<String> list = new ArrayList<>();
//        list.add("555088-105");摩卡
//        list.add("DC2198-100");
//        list.add("CT8532-050");
//        list.add("575441-029");
//        list.add("CT8532-050");
//        try {
//            while(temp<4){
//                    String search = sage.search("BA5751-072");
//                    List<String> idList = sage.commodityIdList(search);
//                    for(String id: idList){
//                        sage.commodityDetail(id);
//                    }
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        while (true){
            try {
                long startTime = System.currentTimeMillis();
                String detail =sage.commodityDetail("4cfca74bf3d84a35a317c4fb899df2c5");
                long endTime = System.currentTimeMillis();
                System.out.println("程序运行时间：" + (endTime - startTime) + "ms");
            } catch (Exception e) {
                e.printStackTrace();
            }
//        }

    }



    private  String search(String keyword) throws Exception{
        String url = "https://wxmall-lv.topsports.com.cn/search/shopCommodity/list";
        HashMap<String, String> map = new HashMap<>();
        map.put("searchKeyword",keyword);
        map.put("current","1");
        map.put("pageSize","20");
        map.put("sortColumn","");
        map.put("sortType","asc");
        map.put("filterIds","");
        map.put("shopNo","");//NKND20
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
        String url = commodityUrl + id;
        HttpURLConnection conn = HttpUtil.getConn(url, null);
        String result = HttpUtil.get(conn);
        JSONObject sult = JSONObject.parseObject(result);
        JSONObject data = (JSONObject)sult.get("data");
        if ("3".equals(data.get("status").toString())){
            if ((int)data.get("stock") >0){
                JSONArray skuList = (JSONArray) data.get("skuList");
//                for (int i = 0; i < skuList.size(); i++) {
                    JSONObject info = (JSONObject)skuList.get(0);
                    if ((int)info.get("stock") >0){
                        System.out.println("有货售卖："+id);
//                        System.out.println(sult);
                        JSONObject jsonObject = JSONObject.parseObject(param);
                        JSONArray array = jsonObject.getJSONArray("subOrderList");
                        JSONObject subOrderList = (JSONObject) array.get(0);
                        subOrderList.put("shopNo",data.get("shopNo").toString());//设置店铺号
                        JSONArray arrayOne = subOrderList.getJSONArray("commodityList");
                        JSONObject commodityList = (JSONObject) arrayOne.get(0);

                        JSONObject cartJS = JSONObject.parseObject(cartCode);
                        cartJS.put("shopNo",data.get("shopNo").toString());
                        cartJS.put("productCode",data.get("productCode").toString());
                        cartJS.put("productSkuNo",info.get("skuNo").toString());
                        cartJS.put("productSizeCode",info.get("sizeCode").toString());
                        cartJS.put("productSkuId",info.get("id").toString());
                        cartJS.put("shopCommodityId",id);
                        send(cartCode.toString(),addCartUrl);
                        conn = HttpUtil.getConn(getCartUrl, null);
                        conn.setRequestProperty("Authorization",token);
                        result = HttpUtil.get(conn);
                        JSONObject CartData = (JSONObject)JSONObject.parseObject(result).get("data");
                        CartData = JSONObject.parseObject(JSON.parseArray(CartData.get("willBuyList").toString(), String.class).get(0));
                        String buyCommodityVOList = JSONArray.parseArray(CartData.get("buyCommodityVOList").toString(), String.class).get(0);
                        String shoppingcartId = JSONObject.parseObject(buyCommodityVOList).get("shoppingcartId").toString();
//                        //下单参数
                        commodityList.put("productCode",data.get("productCode").toString());
                        commodityList.put("shoppingcartId",shoppingcartId);
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
                        send(jsonObject.toString(),createOrder);
                        temp++;
                    }
//                }
            }
        }
        return result;
    }



    public String send(String requestBody,String url) throws Exception{

        String body = "";
        //创建post方式请求对象

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        //装填参数
        StringEntity s = new StringEntity(requestBody, "utf-8");
        //设置参数到请求对象中
        httpPost.setEntity(s);
        //设置header信息
        httpPost.setHeader(":Host","wxmall-lv.topsports.com.cn");
        httpPost.setHeader("Connection","keep-alive");
        httpPost.setHeader("Authorization",token);
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
        System.out.println(body);
        return body;
    }


    private static String cartCode = "{\n" +
            "    \"shopNo\":\"NKCQ54\",\n" +
            "    \"productCode\":\"CV1753-101\",\n" +
            "    \"productSkuNo\":\"20201130007910\",\n" +
            "    \"productSizeCode\":\"6.5\",\n" +
            "    \"productSkuId\":\"314fcf0410c04ab3a4c00ad491be699c\",\n" +
            "    \"shopCommodityId\":\"99fa3572913e452fa8f0bdc197aed43b\",\n" +
            "    \"brandNo\":\"NK\",\n" +
            "    \"num\":1,\n" +
            "    \"merchantNo\":\"TS\",\n" +
            "    \"liveType\":0,\n" +
            "    \"roomId\":\"\",\n" +
            "    \"roomName\":\"\"\n" +
            "}";

    private static String param = "{\n" +
            "    \"merchantNo\":\"TS\",\n" +
            "    \"rid\":\"\",\n" +
            "    \"shippingId\":\"8a7a08b57651ae6a01765cb5e70f7b1c\",\n" +
            "    \"subOrderList\":[\n" +
            "        {\n" +
            "            \"shopNo\":\"NKCQ54\",\n" +
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
            "                    \"shoppingcartId\":\"198e1bbc1ccc42f99e2828a836c0555e\",\n" +
            "                    \"paterId\":null,\n" +
            "                    \"productCode\":\"CV1753-101\",\n" +
            "                    \"productNo\":\"20200927001161\",\n" +
            "                    \"colorNo\":\"00\",\n" +
            "                    \"colorName\":\"默认\",\n" +
            "                    \"sizeNo\":\"20160426000041\",\n" +
            "                    \"sizeCode\":\"6.5\",\n" +
            "                    \"sizeEur\":\"39\",\n" +
            "                    \"brandDetailNo\":\"NK01\",\n" +
            "                    \"proNo\":null,\n" +
            "                    \"proName\":null,\n" +
            "                    \"assignProNo\":\"0\",\n" +
            "                    \"skuId\":\"314fcf0410c04ab3a4c00ad491be699c\",\n" +
            "                    \"skuNo\":\"20201130007910\",\n" +
            "                    \"shopCommodityId\":\"99fa3572913e452fa8f0bdc197aed43b\",\n" +
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
            "                    \"zoneQsLevel\":\"H\",\n" +
            "                    \"live_type\":0,\n" +
            "                    \"room_id\":\"\",\n" +
            "                    \"room_name\":\"\"\n" +
            "                }\n" +
            "            ],\n" +
            "            \"ticketCodes\":null,\n" +
            "            \"vipPrefAmount\":\"0.00\",\n" +
            "            \"prefAmount\":\"0.00\",\n" +
            "            \"ticketDtos\":[\n" +
            "\n" +
            "            ],\n" +
            "            \"unTicketDtos\":[\n" +
            "\n" +
            "            ],\n" +
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
            "    \"validate\":\"5c57ac04ffab573b7728e8056c6182d8\",\n" +
            "    \"seccode\":\"5c57ac04ffab573b7728e8056c6182d8|jordan\",\n" +
            "    \"challenge\":\"cb5955d2871affe931e4866e0cd1550a\"\n" +
            "}";


}
