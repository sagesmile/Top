package topKill;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.lang.reflect.Parameter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Sage {

    private static String validate = "4c1466da59725a90aed4213653c0a26f";
    private static String challenge = "2a987f8ee38cf25876f632c3ff547335";
    private static String token = "97b682c0-d9bb-4409-82d2-9372031922dc";

    public static void main(String[] args) throws Exception{
        commodityDetail("b888a81b4dc442318f1f9f581842c455");
    }

    public static String commodityDetail(String id) throws Exception{
        String url = "https://wxmall-lv.topsports.com.cn/shopCommodity/queryShopCommodityDetail/" + id;
        String body = "";
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("accept", "*/*");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36 MicroMessenger/7.0.9.501 NetType/WIFI MiniProgramEnv/Windows WindowsWechat");
        httpGet.setHeader("Proxy-Connection", "Keep-Alive");
        httpGet.setHeader("Referer","https://servicewechat.com/wx71a6af1f91734f18/21/page-frame.html");
        httpGet.setHeader("Accept-Encoding","gzip, deflate, br");
        httpGet.setHeader("content-type","application/json");
        CloseableHttpResponse response = client.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            body = EntityUtils.toString(entity, "utf-8");
        }
        EntityUtils.consume(entity);
        response.close();
        JSONObject sult = JSONObject.parseObject(body);
        JSONObject data = (JSONObject)sult.get("data");
        if ("3".equals(data.get("status").toString())) {
            if ((int) data.get("stock") > 0) {
                JSONArray skuList = (JSONArray) data.get("skuList");
                for (int i = 0; i < skuList.size(); i++) {
                    JSONObject info = (JSONObject)skuList.get(i);
                    if ((int)info.get("stock") >0){
                        JSONObject jsonObject = JSONObject.parseObject(param);
                        jsonObject.put("validate",validate);
                        jsonObject.put("seccode",validate+"|jordan");
                        jsonObject.put("challenge",challenge);
                        send(jsonObject);
                        break;
                    }
                }
            }
        }
        return null;
    }

    private static void send(JSONObject param) throws Exception{
        String url = "https://wxmall-lv.topsports.com.cn/order/create";
        String body = "";
        //创建post方式请求对象

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        //装填参数
        StringEntity s = new StringEntity(param.toString(), "utf-8");
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
    }

    private static String param = "{\n" +
            "    \"shippingId\":\"8a7a099875bbd1df0175e5964d5a6f06\",\n" +
            "    \"usedPlatformCouponList\":[\n" +
            "\n" +
            "    ],\n" +
            "    \"seccode\":\"|jordan\",\n" +
            "    \"challenge\":\"\",\n" +
            "    \"verificationType\":2,\n" +
            "    \"subOrderList\":[\n" +
            "        {\n" +
            "            \"expressAmount\":\"0.00\",\n" +
            "            \"virtualShopFlag\":0,\n" +
            "            \"expressType\":2,\n" +
            "            \"cashOnDelivery\":0,\n" +
            "            \"commodityList\":[\n" +
            "                {\n" +
            "                    \"shoppingcartId\":\"720e22f0ce5140f686f37a2feedfa3ad\",\n" +
            "                    \"room_id\":\"\",\n" +
            "                    \"liveType\":0,\n" +
            "                    \"num\":1,\n" +
            "                    \"live_type\":0,\n" +
            "                    \"brandDetailNo\":\"NK01\",\n" +
            "                    \"shopCommodityId\":\"b888a81b4dc442318f1f9f581842c455\",\n" +
            "                    \"colorNo\":\"00\",\n" +
            "                    \"map\":{\n" +
            "\n" +
            "                    },\n" +
            "                    \"productNo\":\"20201217000059\",\n" +
            "                    \"skuId\":\"c153953aa82146dca38f34794f354cb1\",\n" +
            "                    \"sizeNo\":\"20160426000043\",\n" +
            "                    \"assignProNo\":\"0\",\n" +
            "                    \"roomName\":\"\",\n" +
            "                    \"room_name\":\"\",\n" +
            "                    \"productCode\":\"CT8013-602\",\n" +
            "                    \"itemFlag\":0,\n" +
            "                    \"skuNo\":\"20201217221806\",\n" +
            "                    \"sizeCode\":\"7.5\",\n" +
            "                    \"activityType\":0,\n" +
            "                    \"status\":3\n" +
            "                }\n" +
            "            ],\n" +
            "            \"couponAmount\":\"0.00\",\n" +
            "            \"totalNum\":1,\n" +
            "            \"vipPrefAmount\":\"0.00\",\n" +
            "            \"prefAmount\":\"0.00\",\n" +
            "            \"shopNo\":\"NKCD94\",\n" +
            "            \"promotionAmount\":\"0.00\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"rid\":\"202011151614220b6ed9f5bea80eefe3\",\n" +
            "    \"merchantNo\":\"TS\",\n" +
            "    \"purchaseType\":2,\n" +
            "    \"validate\":\"\"\n" +
            "}";
}
