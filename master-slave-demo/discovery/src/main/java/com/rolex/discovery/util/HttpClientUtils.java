package com.rolex.discovery.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@Slf4j
public class HttpClientUtils {

    public static Map<String, Object> doGet(String url) {
        return doGet(url, Collections.emptyMap());
    }

    public static Map<String, Object> doPost(String url, Map<String, Object> parameter) {
        return doPost(url, parameter, Collections.emptyMap());
    }

    public static Map<String, Object> doGet(String url, Map<String, String> headers) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        log.debug("Send Http Request: url={}, headers={}", url, JSON.toJSON(headers));

        HttpGet get = new HttpGet(url);
        setHeader(get, headers);

        try (CloseableHttpResponse response = httpClient.execute(get)) {
            log.debug("GET Http Response: url={}, headers={}, httpStatus={}", url, JSON.toJSON(headers), response.getStatusLine().getStatusCode());
            return parseResponse(get, response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, Object> doPost(String url, Map<String, Object> parameter, Map<String, String> headers) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        log.debug("Send Http Request: url={}, headers={}, parameter={}", url, JSON.toJSON(headers), JSON.toJSON(parameter));

        HttpPost httpPost = new HttpPost(url);
        setHeader(httpPost, headers);

        CloseableHttpResponse response = null;
        try {
            httpPost.setEntity(new StringEntity(JSON.toJSONString(parameter)));
            response = httpClient.execute(httpPost);
            log.debug("GET Http Response: url={}, result={}", url, response);
            return parseResponse(httpPost, response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            closeableHttpResponse(response);
        }
    }

    public static File downloadFile(String url, String directory, String fileName) {
        return downloadFile(url, directory, fileName, Collections.emptyMap());
    }

    public static File downloadFile(String url, String directory, String fileName, Map<String, String> headers) {
        log.info("Start Download File: url={}, headers={}", url, headers);

        HttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);

        setHeader(httpGet, headers);

        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        try {
            HttpResponse response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            in = new BufferedInputStream(entity.getContent());

            File file = new File(directory, StringUtils.isEmpty(fileName) ? getFileNameOrDefault(response, "Opts") : fileName);
            out = new BufferedOutputStream(new FileOutputStream(file));

            IOUtils.write(in, out);
            return file;
        } catch (IOException e) {
            throw new RuntimeException("Download File Error", e);
        } finally {
            IOUtils.closeQuietly(in, out);
        }
    }

    private static Map<String, Object> parseResponse(HttpRequestBase request, CloseableHttpResponse response) throws IOException {
        if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            throw new RuntimeException(String.format("Send Http Request Error, request=%s, response=%s", request.toString(), getResponseBody(response)));
        }
        Map<String, Object> responseData = JSON.<Map<String, Object>>parseObject(getResponseBody(response), Map.class);
        return validateBusinessStatus(responseData);
    }

    private static String getFileNameOrDefault(HttpResponse response, String defaultFileName) {
        Header contentHeader = response.getFirstHeader("Content-Disposition");
        if (contentHeader == null) {
            return defaultFileName;
        }

        HeaderElement[] elements = contentHeader.getElements();
        if (elements.length != 1) {
            return defaultFileName;
        }

        NameValuePair element = elements[0].getParameterByName("filename");
        if (element == null) {
            return defaultFileName;
        }
        return element.getValue();
    }

    private static void setHeader(HttpRequestBase request, Map<String, String> headers) {
        if (!headers.containsKey("Content-Type")) {
            request.addHeader("Content-Type", "application/json;charset=UTF-8");
        }
        headers.forEach((k, v) -> request.addHeader(k, v));
    }

    private static String getResponseBody(CloseableHttpResponse response) throws IOException {
        try {
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    private static void closeableHttpResponse(CloseableHttpResponse response) {
        try {
            if (response != null) {
                response.close();
            }
        } catch (IOException e) {
            log.error("Close HttpResponse Error", e);
        }
    }

    private static Map<String, Object> validateBusinessStatus(Map<String, Object> responseData) {
        if (!responseData.containsKey("status") || Integer.valueOf(String.valueOf(responseData.get("status"))) != 200) {
            throw new RuntimeException(String.format("Bad Response Status: %s", JSON.toJSONString(responseData)));
        }
        return responseData;
    }
}