package com.xuanru.util;

import com.alibaba.fastjson.JSONObject;
import com.xuanru.common.Constants;
import com.xuanru.dto.HttpNetResponse;
import com.xuanru.dto.exception.SystemError;
import com.xuanru.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.http.*;
import org.apache.http.message.BasicNameValuePair;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class HttpNetClientUtil {

    private static int time_out = 3 * 1000;

    //    public String doPut(String uri, String jsonObj, String encode) throws Exception {
//        String resStr = null;
//        org.apache.commons.httpclient.HttpClient htpClient = new org.apache.commons.httpclient.HttpClient();
//        PutMethod putMethod = new PutMethod("");
//        putMethod.addRequestHeader("Content-Type", "application/json");
//        putMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
//        putMethod.setRequestBody(jsonObj);
//        int statusCode = htpClient.executeMethod(putMethod);
//        try {
//            if (statusCode == HttpStatus.SC_OK) {
//                InputStream inputStream = putMethod.getResponseBodyAsStream();
//                return changeInputStream(inputStream, encode);
//            } else {
//                log.error("http PUT Method failed: " + JSONObject.toJSONString(putMethod.getStatusLine()));
//            }
//        } finally {
//            putMethod.releaseConnection();
//        }
//
//        return null;
//    }

    private static HttpClient getHttpClient(String host, int port, String protocol) {
        HttpClient client = new HttpClient();
        client.getHostConfiguration().setHost(host, port, protocol);
        client.getHttpConnectionManager().getParams().setConnectionTimeout(time_out);
        return client;
    }

    public static String sendHttpClientGet(String host, int port, String protocol, String path, String encode) throws Exception {
        HttpClient client = new HttpClient();
        client.getHostConfiguration().setHost(host, port, protocol);
        client.getHttpConnectionManager().getParams().setConnectionTimeout(time_out);
        HttpMethod method = new GetMethod(path);
        int statusCode = client.executeMethod(method);   //打印服务器返回的状态
        String url = client.getHostConfiguration().getHostURL() + method.getURI().toString();
        log.info("http GET url={} statusCode=[{}]", url, statusCode);
        try {
            if (statusCode == HttpStatus.SC_OK) {
                InputStream inputStream = method.getResponseBodyAsStream();
                return changeInputStream(inputStream, encode);
            } else {
                log.error("http GET Method failed: " + JSONObject.toJSONString(method.getStatusLine()));
            }
        } finally {
            method.releaseConnection();
        }

        return null;
    }

    public static HttpNetResponse sendHttpClientGet(String url, String encode) {
        HttpClient client = new HttpClient();
        GetMethod method = new GetMethod();
        HttpNetResponse netResponse = new HttpNetResponse();
        try {
            client.getHostConfiguration().setHost(new HttpURL(url));
            client.getHttpConnectionManager().getParams().setConnectionTimeout(time_out);
            int statusCode = client.executeMethod(method);
            log.info("http GET url={} statusCode=[{}]", url, statusCode);
            netResponse.setStatusCode(statusCode);
            if (statusCode == HttpStatus.SC_OK) {
                InputStream inputStream = method.getResponseBodyAsStream();
                netResponse.setRespContent(changeInputStream(inputStream, encode));
            } else {
                log.error("http GET Method failed: " + JSONObject.toJSONString(method.getStatusLine()));
            }
        } catch (URIException e) {
            log.error("", e);
            netResponse.setError(new SystemError(ErrorEnum.ERROR_20002));
        } catch (Exception e) {
            log.error("", e);
            netResponse.setError(new SystemError(ErrorEnum.ERROR_10001));
        } finally {
            method.releaseConnection();
        }

        return netResponse;
    }

    public static HttpNetResponse sendHttpClientPost(String url, Map<String, String> params, String encode) {
        HttpClient client = new HttpClient();
        HttpMethod method = new PostMethod();
        HttpNetResponse netResponse = new HttpNetResponse();
        try {
            client.getHostConfiguration().setHost(new HttpURL(url));
            client.getHttpConnectionManager().getParams().setConnectionTimeout(time_out);
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            if (params != null && !params.isEmpty()) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    list.add(new NameValuePair(entry.getKey(), entry
                            .getValue()));
                }
            }
            method.setQueryString(list.toArray(new NameValuePair[]{}));

            int statusCode = client.executeMethod(method);
            log.info("http POST url={} statusCode=[{}]", url, statusCode);
            netResponse.setStatusCode(statusCode);
            if (statusCode == HttpStatus.SC_OK) {
                InputStream inputStream = method.getResponseBodyAsStream();
                netResponse.setRespContent(changeInputStream(inputStream, encode));
            } else {
                log.error("http POST Method failed: " + JSONObject.toJSONString(method.getStatusLine()));
            }
        } catch (URIException e) {
            log.error("", e);
            netResponse.setError(new SystemError(ErrorEnum.ERROR_20002));
        } catch (Exception e) {
            log.error("", e);
            netResponse.setError(new SystemError(ErrorEnum.ERROR_10001));
        } finally {
            method.releaseConnection();
        }

        return netResponse;
    }

    public static HttpNetResponse sendHttpClientPost(String host, int port, String protocol, String path, Map<String, String> params, String encode) {
        HttpClient client = getHttpClient(host, port, protocol);
        HttpMethod method = new PostMethod(path);
        HttpNetResponse netResponse = new HttpNetResponse();
        try {
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            if (params != null && !params.isEmpty()) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    list.add(new NameValuePair(entry.getKey(), entry
                            .getValue()));
                }
            }
            method.setQueryString(list.toArray(new NameValuePair[]{}));

            int statusCode = client.executeMethod(method);
            netResponse.setStatusCode(statusCode);
            if (statusCode == HttpStatus.SC_OK) {
                InputStream inputStream = method.getResponseBodyAsStream();
                netResponse.setRespContent(changeInputStream(inputStream, encode));
            } else {
                log.error("http POST Method failed: " + JSONObject.toJSONString(method.getStatusLine()));
            }
        } catch (URIException e) {
            log.error("", e);
            netResponse.setError(new SystemError(ErrorEnum.ERROR_20002));
        } catch (Exception e) {
            log.error("", e);
            netResponse.setError(new SystemError(ErrorEnum.ERROR_10001));
        } finally {
            method.releaseConnection();
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
        System.out.println("HTTP POST-result->>" + JSONObject.toJSONString(sendHttpClientPost("localhost", 8080, "http", "/login", params, "utf-8")));

//        String path = "http://localhost:8080/test/put";
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("username", "你好");
//        params.put("pwd", "1234");
//        String result = null;
//        try {
//            result = sendHttpClientPut(path, params, "utf-8");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println("http PUT -result->>" + result);

        try {
            System.out.println("http GET -result->>" + JSONObject.toJSONString(sendHttpClientGet("http://10.10.53.180:16774/fengfd/api/v2/special/induction/newbies/recommend?version=1.0", "utf-8")));
            System.out.println("http GET -result->>" + JSONObject.toJSONString(sendHttpClientGet("10.10.53.180", 16774, "http", "/fengfd/api/v2/special/induction/newbies/recommend?version=1.0", "utf-8")));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}