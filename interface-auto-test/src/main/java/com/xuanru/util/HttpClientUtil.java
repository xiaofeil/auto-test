package com.xuanru.util;

import com.alibaba.fastjson.JSONObject;
import com.xuanru.common.Constants;
import com.xuanru.dto.HttpNetResponse;
import com.xuanru.dto.exception.SystemError;
import com.xuanru.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class HttpClientUtil {

    private static int time_out = 3 * 1000;

    /**
     * 用apache接口实现http的GET提交数据
     *
     * @param
     * @return
     * @Author Liaoxf
     * @Date 2016-09-29 11:02
     */
    public static HttpNetResponse sendHttpClientGet(String url, String encode) {

        HttpNetResponse netResponse = null;
        if (StringUtil.isBlank(url)) {
            log.info("url is null");
            return null;
        }
        // 使用post方式提交数据
        HttpGet httpGet = null;
        try {
            httpGet = new HttpGet(url);
            // 执行post请求，并获取服务器端的响应HttpResponse
            HttpClient client = getHttpClient();
            HttpResponse httpResponse = client.execute(httpGet);
            log.info("http GET url=[{}]", url);
            netResponse = parseResponse(httpResponse, encode);
        } catch (Exception e) {
            log.error("", e);
            netResponse = new HttpNetResponse();
            netResponse.setError(new SystemError(ErrorEnum.ERROR_10001));
        } finally {
            if (httpGet != null)
                httpGet.releaseConnection();
        }

        return netResponse;

    }

    private static HttpNetResponse parseResponse(HttpResponse httpResponse, String encode) {
        HttpNetResponse netResponse = new HttpNetResponse();
        try {
            //获取服务器端返回的状态码和输入流，将输入流转换成字符串
            if (httpResponse.getStatusLine() != null) {
                netResponse.setStatusCode(httpResponse.getStatusLine().getStatusCode());
                if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    InputStream inputStream = httpResponse.getEntity().getContent();
                    netResponse.setRespContent(changeInputStream(inputStream, encode));
                }
            } else {
                netResponse.setError(new SystemError(ErrorEnum.ERROR_20001));
            }
        } catch (Exception e) {
            log.error("", e);
            netResponse.setError(new SystemError(ErrorEnum.ERROR_10001));
        }
        return netResponse;
    }

    private static HttpClient getHttpClient() {
        HttpClient client = new DefaultHttpClient();

        HttpParams params = client.getParams();
        HttpConnectionParams.setConnectionTimeout(params, time_out); // 连接超时
        HttpConnectionParams.setSoTimeout(params, time_out); // 读取超时
        return client;
    }

    /**
     * 用apache接口实现http的POST提交数据
     *
     * @param
     * @return
     * @Author Liaoxf
     * @Date 2016-09-29 11:02
     */
    public static HttpNetResponse sendHttpClientPost(String url, Map<String, String> params, String encode) {

        if (StringUtil.isBlank(url)) {
            log.info("url is null");
            return null;
        }

        // 使用post方式提交数据
        HttpPost httpPost = null;
        HttpNetResponse netResponse = null;
        try {
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            if (params != null && !params.isEmpty()) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    list.add(new BasicNameValuePair(entry.getKey(), entry
                            .getValue()));
                }
            }

            httpPost = new HttpPost(url);
            // 实现将请求的参数封装到表单中，即请求体中
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, encode);
            httpPost.setEntity(entity);
            // 执行post请求，并获取服务器端的响应HttpResponse
            HttpClient client = getHttpClient();
            HttpResponse httpResponse = client.execute(httpPost);
            log.info("http POST url=[{}]", url);
            netResponse = parseResponse(httpResponse, encode);
        } catch (Exception e) {
            log.error("", e);
            netResponse = new HttpNetResponse();
            netResponse.setError(new SystemError(ErrorEnum.ERROR_10001));
        } finally {
            if (httpPost != null)
                httpPost.releaseConnection();
        }

        return netResponse;

    }

    /**
     * 用apache接口实现http的PUT提交数据
     *
     * @param
     * @return
     * @Author Liaoxf
     * @Date 2016-09-29 15:23
     */
    public static HttpNetResponse sendHttpClientPut(String url, Map<String, String> params, String encode) {

        if (StringUtil.isBlank(url)) {
            log.info("url is null");
            return null;
        }

        HttpNetResponse netResponse = null;
        // 使用post方式提交数据
        HttpPut httpPut = null;
        try {
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            if (params != null && !params.isEmpty()) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    list.add(new BasicNameValuePair(entry.getKey(), entry
                            .getValue()));
                }
            }

            httpPut = new HttpPut(url);
            // 实现将请求的参数封装到表单中，即请求体中
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, encode);
            httpPut.setEntity(entity);
            // 执行post请求，并获取服务器端的响应HttpResponse
            HttpClient client = getHttpClient();
            HttpResponse httpResponse = client.execute(httpPut);

            log.info("http PUT url=[{}]", url);
            netResponse = parseResponse(httpResponse, encode);
        } catch (Exception e) {
            log.error("", e);
            netResponse = new HttpNetResponse();
            netResponse.setError(new SystemError(ErrorEnum.ERROR_10001));
        } finally {
            if (httpPut != null)
                httpPut.releaseConnection();
        }

        return netResponse;

    }

    /**
     * 把从输入流InputStream按指定编码格式encode变成字符串String
     *
     * @param
     * @return
     * @Author Liaoxf
     * @Date 2016-09-29 15:07
     */
    public static String changeInputStream(InputStream inputStream,
                                           String encode) throws Exception {
        encode = StringUtil.isBlank(encode) ? Constants.CHARSET_UTF_8 : encode;
        // ByteArrayOutputStream 一般叫做内存流
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        String result = "";
        if (inputStream != null) {
            while ((len = inputStream.read(data)) != -1) {
                byteArrayOutputStream.write(data, 0, len);
            }
            result = new String(byteArrayOutputStream.toByteArray(), encode);
        }

        return result;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        String path = "http://localhost:8080/login";
        Map<String, String> params = new HashMap<String, String>();
        params.put("username", "你好");
        params.put("pwd", "123");
        System.out.println("HTTP POST-result->>" + JSONObject.toJSONString(sendHttpClientPost(path, params, "utf-8")));

//        String path = "http://localhost:8080/test/put";
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("username", "你好");
//        params.put("pwd", "1234");
//        System.out.println("http PUT -result->>" + JSONObject.toJSONString(sendHttpClientPut(path, params, "utf-8")));

        try {
//            System.out.println("http GET -result->>" + sendHttpClientGet("10.10.53.180", 16774, "http", "/fengfd/api/v2/special/induction/newbies/recommend?version=1.0", "utf-8"));
            System.out.println("http GET -result->>" + JSONObject.toJSONString(sendHttpClientGet("http://10.10.53.180:16774/fengfd/api/v2/special/induction/newbies/recommend?version=1.0", "utf-8")));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}