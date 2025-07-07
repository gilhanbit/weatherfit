package com.weatherfit.naver.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Component
public class SearchShopAPI {

    @Value("${naver.client-id}")
    private String clientId;
    @Value("${naver.client-secret}")
    private String clientSecret;

    public String callNaverSearchShopAPIByUserTop(String userTop) {

        // TODO 유저 스타일+기온에 맞는 차림 받아와서 -> urlencoder에 넣기
        String text = null;
        int display = 30;

        try {
            text = URLEncoder.encode(userTop, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패",e);
        }


        String apiURL = "https://openapi.naver.com/v1/search/shop?query=" + text + "&display=" + display; // JSON 결과
//        String apiURL = "https://openapi.naver.com/v1/search/shop.xml?query=" + text + "&display=" + display; // XML 결과


        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String responseBodyUserTop = get(apiURL,requestHeaders);


//        System.out.println(responseBody);
        return responseBodyUserTop;
    }


    public String callNaverSearchShopAPIByTop(String noUserTop) {

        // TODO 유저 스타일+기온에 맞는 차림 받아와서 -> urlencoder에 넣기
        String text = null;
        int display = 30;

        try {
            text = URLEncoder.encode(noUserTop, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패",e);
        }


        String apiURL = "https://openapi.naver.com/v1/search/shop?query=" + text + "&display=" + display; // JSON 결과
//        String apiURL = "https://openapi.naver.com/v1/search/shop.xml?query=" + text + "&display=" + display; // XML 결과


        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String responseBodyTop = get(apiURL,requestHeaders);


//        System.out.println(responseBody);
        return responseBodyTop;
    }


    private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }


            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 오류 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }


    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }


    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);


        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();


            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }


            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
        }
    }
}