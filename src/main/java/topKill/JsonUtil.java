package topKill;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class JsonUtil {

    public static void main(String[] args) throws Exception {

        commodityDetail("b888a81b4dc442318f1f9f581842c455");
    }

    public static void commodityDetail(String id) throws Exception {
        String url = "https://wxmall-lv.topsports.com.cn/shopCommodity/queryShopCommodityDetail/" + id;
        String body = "";
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("accept", "*/*");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36 MicroMessenger/7.0.9.501 NetType/WIFI MiniProgramEnv/Windows WindowsWechat");
        httpGet.setHeader("Proxy-Connection", "Keep-Alive");
        httpGet.setHeader("Referer", "https://servicewechat.com/wx71a6af1f91734f18/21/page-frame.html");
        httpGet.setHeader("Accept-Encoding", "gzip, deflate, br");
        httpGet.setHeader("content-type", "application/json");
        CloseableHttpResponse response = client.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            body = EntityUtils.toString(entity, "utf-8");
        }
        EntityUtils.consume(entity);
        response.close();
        JSONObject sult = JSONObject.parseObject(body);
        JSONObject data = (JSONObject) sult.get("data");
        if ("3".equals(data.get("status").toString())) {
            if ((int) data.get("stock") > 0) {
                JSONArray skuList = (JSONArray) data.get("skuList");
                for (int i = 0; i < skuList.size(); i++) {
                    JSONObject info = (JSONObject) skuList.get(i);
                    if ((int) info.get("stock") > 0) {
                        JSONObject jsonObject = JSONObject.parseObject(param);
                        JSONArray array = jsonObject.getJSONArray("subOrderList");
                        JSONObject subOrderList = (JSONObject) array.get(0);
                        subOrderList.put("shopNo", data.get("shopNo").toString());//设置店铺号
                        JSONArray arrayOne = subOrderList.getJSONArray("commodityList");
                        JSONObject commodityList = (JSONObject) arrayOne.get(0);
                        commodityList.put("productCode", data.get("productCode").toString());
                        commodityList.put("productNo", data.get("productNo").toString());
                        commodityList.put("sizeNo", info.get("sizeNo").toString());
                        commodityList.put("sizeCode", info.get("sizeCode").toString());
                        commodityList.put("brandDetailNo", data.get("brandDetailNo").toString());
                        commodityList.put("skuId", info.get("id").toString());
                        commodityList.put("skuNo", info.get("skuNo").toString());
                        commodityList.put("shopCommodityId", data.get("id").toString());
                        arrayOne.set(0, commodityList);
                        subOrderList.put("commodityList", arrayOne);
                        array.set(0, subOrderList);
                        jsonObject.put("subOrderList", array);
                        System.out.println(jsonObject);
                    }
                }
            }
        }
    }

    private static String param = "{\n" +
            "    \"merchantNo\":\"TS\",\n" +
            "    \"rid\":\"202011151614220b6ed9f5bea80eefe3\",\n" +
            "    \"shippingId\":\"8a7a099875bbd1df0175e5964d5a6f06\",\n" +
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
            "                    \"shoppingcartId\":\"720e22f0ce5140f686f37a2feedfa3ad\",\n" +
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



