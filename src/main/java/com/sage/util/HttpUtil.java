package com.sage.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

/**
 * HTTP工具
 * @author robinzhang
 *
 */
public class HttpUtil {
    /**
     * 请求类型： GET
     */
    public final static String GET = "GET";
    /**
     * 请求类型： POST
     */
    public final static String POST = "POST";

    /**
     * 模拟Http Get请求
     * @param urlStr
     *             请求路径
     * @param paramMap
     *             请求参数
     * @return
     * @throws Exception
     */
    public static HttpURLConnection getConn(String urlStr, Map<String, String> paramMap,HttpURLConnection conn) {
        urlStr = urlStr + "?" + getParamString(paramMap);
        System.out.println(urlStr);
        try {
            //创建URL对象
            URL url = new URL(urlStr);
            //获取URL连接
            conn = (HttpURLConnection) url.openConnection();
        }catch (IOException e){
            e.printStackTrace();
        }
        return conn;
    }
    public static String get(HttpURLConnection conn) throws Exception{

        try {
            //设置通用的请求属性
            setHttpUrlConnection(conn, GET);
            //建立实际的连接
            conn.connect();
            //获取响应的内容
            return readResponseContent(conn.getInputStream());
        }finally{
            if(null!=conn) conn.disconnect();
        }
    }

    /**
     * 模拟Http Post请求
     * @param urlStr
     *             请求路径
     * @return
     * @throws Exception
     */
    public static HttpURLConnection postConn(String urlStr,HttpURLConnection conn) throws Exception {
        //创建URL对象
        URL url = new URL(urlStr);

        //获取URL连接
        conn = (HttpURLConnection) url.openConnection();

        return conn;
    }

    public static String post( Map<String, String> paramMap,HttpURLConnection conn) throws Exception{

        PrintWriter writer = null;
        //获取请求参数
        String param = getParamString(paramMap);
            try{
            //设置通用请求属性
            setHttpUrlConnection(conn, POST);
            //建立实际的连接
            conn.connect();
            //将请求参数写入请求字符流中
            writer = new PrintWriter(conn.getOutputStream());
            writer.print(param);
            writer.flush();
            //读取响应的内容
            return readResponseContent(conn.getInputStream());
        }finally{
            if(null!=conn) conn.disconnect();
            if(null!=writer) writer.close();
        }
    }

    /**
     * 读取响应字节流并将之转为字符串
     * @param in
     *         要读取的字节流
     * @return
     * @throws IOException
     */
    private static String readResponseContent(InputStream in) throws IOException{
        Reader reader = null;
        StringBuilder content = new StringBuilder();
        try{
            reader = new InputStreamReader(in);
            char[] buffer = new char[1024];
            int head = 0;
            while( (head=reader.read(buffer))>0 ){
                content.append(new String(buffer, 0, head));
            }
            return content.toString();
        }finally{
            if(null!=in) in.close();
            if(null!=reader) reader.close();
        }
    }

    /**
     * 设置Http连接属性
     * @param conn
     *             http连接
     * @return
     * @throws ProtocolException
     * @throws Exception
     */
    private static void setHttpUrlConnection(HttpURLConnection conn, String requestMethod) throws ProtocolException{
        conn.setRequestMethod(requestMethod);
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36 MicroMessenger/7.0.9.501 NetType/WIFI MiniProgramEnv/Windows WindowsWechat");
        conn.setRequestProperty("Proxy-Connection", "Keep-Alive");
//        conn.setRequestProperty("Referer","https://servicewechat.com/wx71a6af1f91734f18/21/page-frame.html");
        conn.setRequestProperty("Accept-Encoding","gzip, deflate, br");
        conn.setRequestProperty("content-type","application/json");
        if(null!=requestMethod && POST.equals(requestMethod)){
            conn.setDoOutput(true);
            conn.setDoInput(true);
        }
    }

    /**
     * 将参数转为路径字符串
     * @param
     * @return
     */
    private static String getParamString(Map<String, String> paramMap){
        if(null==paramMap || paramMap.isEmpty()){
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for(String key : paramMap.keySet() ){
            builder.append("&")
                    .append(key).append("=").append(paramMap.get(key));
        }
        return builder.deleteCharAt(0).toString();
    }

}
