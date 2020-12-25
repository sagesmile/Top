package ruili;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class PostUtil {

    private static String token= "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzZXNzaW9uX2tleSI6Im1CZHdJTzAxQkRWSi9NVzltcFFBeXc9PSIsIm9wZW5pZCI6Im9nQ1NnNHRHeWN3ZDZqWTZLQXBkZzg4eVhuR1EiLCJhcHBpZCI6Ind4NjJhZDljMjE3Yjc5MGIwMSIsInBsYXRmb3JtIjoid3giLCJ1c2VyT3BlbklkIjoib2dDU2c0dEd5Y3dkNmpZNktBcGRnODh5WG5HUSIsInVpZCI6Ind4Lnd4NjJhZDljMjE3Yjc5MGIwMS4ub2dDU2c0dEd5Y3dkNmpZNktBcGRnODh5WG5HUSIsImNyZWF0ZWRfYXQiOjE2MDg3MzY2NjIsImFwaUlkIjoiQ0xJRU5UQ1VTVE9NRVIiLCJncm91cElkIjo0LCJpYXQiOjE2MDg3MzY2NjIsImV4cCI6MTYwODkwOTQ2Mn0.fIvwfCERoLqXrKZDWL0IBDqRtRPYLUg2L-76c1Ht1r0";

    private static Integer siteId = 1719;

    public static void main(String[] args) throws Exception{
//        System.out.println(toBuy(15520,"194501206006","NK554725-077"));
//        System.out.println(getPriTmplId());
            getProduct(15565,0,siteId);

    }

    private static void getProduct(Integer skId,Integer shareId,Integer siteId) throws Exception{
        String productUrl= "https://api.ips-new2.realclub.cn/sec/kill/product/info";
        String path = "";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("skId",skId);
        jsonObject.put("shareId",shareId);
        jsonObject.put("siteId",siteId);
        String body = doPost(productUrl, jsonObject, path);
        JSONObject result = (JSONObject) JSONObject.parse(body);
        String skuItems = ((JSONObject) result.get("content")).get("skuItems").toString();
        String productCode = ((JSONObject) result.get("content")).get("productCode").toString();
        List<String> sizeList = JSONArray.parseArray(skuItems, String.class);
        String code = result.get("code").toString();
        if (code.equals("20000")){
            System.out.print("商品上架啦！！！-----");
        }
        HashMap<String, String> map = new HashMap<>();
        for (String size:sizeList){
            JSONObject detail = (JSONObject) JSONObject.parse(size);
            String stock = detail.get("stock").toString();
            String goodsSku = detail.get("goodsSku").toString();
//            if (Integer.valueOf(stock)>0){
//                String priTmplId = getPriTmplId();
//                String bizId = toBuy(skId, goodsSku, productCode);
//                pushOrder(bizId);
//                break;
//            }
            String sizeName = detail.get("sizeName").toString();
            map.put(sizeName,stock);
        }
        if (map.size() ==0){
            System.out.println("暂无库存");
        }else{
            System.out.println(map);
        }
    }

    private static void pushOrder(String bizId){
        JSONObject object = new JSONObject() {{
            put("bizId", bizId);
            put("siteId", siteId);
        }};
        String url = "https://api.ips-new2.realclub.cn/sec/kill/product/request/state";
        String path = "/sec/kill/product/request/state";
        try {
            String result = doPost(url, object, path);
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String toBuy(Integer skId,String sku,String productCode){
        JSONObject productInfo = new JSONObject() {{
            put("skId", skId);
            put("buyerAddressId", 1608994);
            put("orderRemark", "");
            put("siteId", siteId);
            put("productInfo", new JSONObject() {{
                put("sku", sku);
                put("productCode", productCode);
                put("productNum", 1);
            }});
        }};
        String url = "https://api.ips-new2.realclub.cn/sec/kill/product/buy";
        String path = "/sec/kill/product/buy";
        String body = null;
        try {
            body = doPost(url, productInfo, path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ((JSONObject) ((JSONObject) JSONObject.parse(body)).get("content")).get("bizId").toString();
    }

    private static String getPriTmplId(){
        JSONObject object = new JSONObject() {{
            put("type","skProductOrderSuccess");
            put("siteId",siteId);
        }};
        String url = "https://api.ips-new2.realclub.cn/subScribe/priTmplId/get";
        String path = "/subScribe/priTmplId/get";
        String body = null;
        try {
            body = doPost(url, object, path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject result = (JSONObject)JSONObject.parse(body);
        String priTmplId = ((JSONObject) result.get("content")).get("priTmplId").toString();
        return priTmplId;

    }


    private static String doPost(String url,JSONObject jsonObject,String path)throws IOException {
        String body = "";
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        StringEntity s = new StringEntity(jsonObject.toString(), "utf-8");
        httpPost.setEntity(s);
        httpPost.setHeader("method","Post");
        httpPost.setHeader("authority","api.ips-new2.realclub.cn");
        httpPost.setHeader("scheme","https");
        httpPost.setHeader("path",path);
        httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36 MicroMessenger/7.0.9.501 NetType/WIFI MiniProgramEnv/Windows WindowsWechat");
        httpPost.setHeader("content-type","application/json");
        httpPost.setHeader("referer","https://servicewechat.com/wx62ad9c217b790b01/40/page-frame.html");
        httpPost.setHeader("accept-encoding","gzip, deflate, br");
        httpPost.setHeader("authorization",token);
        CloseableHttpResponse response = client.execute(httpPost);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            //按指定编码转换结果实体为String类型
            body = EntityUtils.toString(entity, "utf-8");
        }
        EntityUtils.consume(entity);
        //释放链接
        response.close();
        return body;
    }
}
