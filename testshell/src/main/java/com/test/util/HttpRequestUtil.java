package com.test.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * http请求工具类
 *
 * @author zhangh
 * @time 2021-03-29 15:11
 */
public class HttpRequestUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestUtil.class);
    private static final String JSON = "application/json";
    private static final String FORM_URLENCODED = "application/x-www-form-urlencoded";

    public HttpRequestUtil() {
    }


    public static String doPost(String url, String params, Map<String, String> headers){
        logger.info("url={}", url);
        logger.info("headerParams={}", headers);
        logger.info("bodyParams={}", params);
        String result = "";
        try{
            HttpURLConnection connection = getConnection(url, params, headers);
            result = writeResponse(connection, params);
        } catch (Exception e){
            e.printStackTrace();
        }
        logger.info("result={}", result);
        return result;
    }


    public static HttpURLConnection getConnection(String url, String contentType, Map<String, String> headers) throws IOException {
        URL conUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) conUrl.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Accept", contentType);
        connection.setRequestProperty("Content-Type", contentType);
        connection.setConnectTimeout(10000);
        if (headers != null) {
            Set<Entry<String, String>> sets = headers.entrySet();
            if (!CollectionUtils.isEmpty(sets)) {
                Iterator iterator = sets.iterator();

                while (iterator.hasNext()) {
                    Entry<String, String> entry = (Entry) iterator.next();
                    connection.setRequestProperty((String) entry.getKey(), (String) entry.getValue());
                }
            }
        }
        return connection;
    }


    public static String writeResponse(HttpURLConnection connection, String requestContent) throws IOException {
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
        out.append(requestContent);
        out.flush();
        out.close();

        Object var6;
        try {
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }

            return result.toString();
        } catch (IOException var16) {
            var16.printStackTrace();
            var6 = null;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException var15) {
                var15.printStackTrace();
            }

        }

        return (String) var6;
    }


    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        Integer ipAddressLength = 15;
        String division = ",";
        String unknown = "unknown";
        String localhost = "127.0.0.1";
        String address = "0:0:0:0:0:0:0:1";
        if (ipAddress == null || ipAddress.length() == 0 || unknown.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }

        if (ipAddress == null || ipAddress.length() == 0 || unknown.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ipAddress == null || ipAddress.length() == 0 || unknown.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals(localhost) || ipAddress.equals(address)) {
                InetAddress inet = null;

                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException var9) {
                    var9.printStackTrace();
                }

                ipAddress = inet.getHostAddress();
            }
        }

        if (ipAddress != null && ipAddress.length() > ipAddressLength && ipAddress.indexOf(division) > 0) {
            ipAddress = ipAddress.substring(0, ipAddress.indexOf(division));
        }

        return ipAddress;
    }


    public static String getPostArgs(HttpServletRequest request) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder("");

        try {
            br = request.getReader();

            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }

            br.close();
        } catch (IOException var12) {
            var12.printStackTrace();
        } finally {
            if (null != br) {
                try {
                    br.close();
                } catch (IOException var11) {
                    var11.printStackTrace();
                }
            }

        }

        return sb.toString();
    }

    public static String doGet(String httpurl) {
        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        String result = null;// 返回结果字符串
        try {
            // 创建远程url连接对象
            URL url = new URL(httpurl);
            // 通过远程url连接对象打开一个连接，强转成httpURLConnection类
            connection = (HttpURLConnection) url.openConnection();
            // 设置连接方式：get
            connection.setRequestMethod("GET");
            // 设置连接主机服务器的超时时间：15000毫秒
            connection.setConnectTimeout(15000);
            // 设置读取远程返回的数据时间：60000毫秒
            connection.setReadTimeout(60000);
            // 发送请求
            connection.connect();
            // 通过connection连接，获取输入流
            if (connection.getResponseCode() == 200) {
                is = connection.getInputStream();
                // 封装输入流is，并指定字符集
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                // 存放数据
                StringBuffer sbf = new StringBuffer();
                String temp = null;
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                result = sbf.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            connection.disconnect();// 关闭远程连接
        }
        return result;
    }

}