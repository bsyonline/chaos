package com.rolex.fsm;

import org.apache.http.HttpHost;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public class Test {
    public static void main(String[] args) throws IOException {
        Test job = new Test();
        RestClient restClient = job.buildESClient("localhost:9200");
        boolean aaa = job.queryIndex(restClient, "aaa");
//        String bbb = job.query(restClient, "schedule_instance_count");
        System.out.println(aaa);
//        System.out.println(bbb);
        restClient.close();
    }

    private RestClient buildESClient(String addrStr) {
        addrStr = addrStr.replaceAll("http://", "");
        HttpHost[] httpHosts = Arrays.stream(addrStr.split(",")).map(str -> {
            String[] arr = str.split(":");
            if (arr.length == 1) {
                return new HttpHost(arr[0], 9200, "http");
            } else {
                return new HttpHost(arr[0], Integer.parseInt(arr[1]), "http");
            }
        }).collect(Collectors.toList()).toArray(new HttpHost[0]);
        return RestClient.builder(httpHosts).build();
    }

    private boolean queryIndex(RestClient restClient, String target) throws IOException {
        Request request = new Request("HEAD", "/" + target);
        Response response = restClient.performRequest(request);
        int code = response.getStatusLine().getStatusCode();
        return code == 200;
    }
    private String query(RestClient restClient, String target) throws IOException {
        Request request = new Request("GET", "/" + target + "/_search");
        Response response = restClient.performRequest(request);
        int code = response.getStatusLine().getStatusCode();
        String responseStr = EntityUtils.toString(response.getEntity());
        return responseStr;
    }
}
