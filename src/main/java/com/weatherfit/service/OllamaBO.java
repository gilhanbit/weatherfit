//package com.weatherfit.service;
//
//import com.weatherfit.naver.api.SearchShopAPI;
//import com.weatherfit.naver.domain.SearchShop;
//import com.weatherfit.naver.service.SearchShopBO;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.io.IOException;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.List;
//
//@RequiredArgsConstructor
//@Service
//public class OllamaBO {
//
//    private final SearchShopAPI searchShopAPI;
//    private final SearchShopBO searchShopBO;
//
//    @Value("${ollama.base-url}")
//    private String ollamaBaseUrl;
//
//    @Value("${ollama.model}")
//    private String modelName;
//
//    public List<SearchShop> getRecommendedKeywords(List<String> keywords, int age) throws IOException {
//
//        String joined = String.join(", ", keywords);
//        String prompt = joined + "와 비슷한 옷 키워드를 사용해서" + age + "대 미니멀 반팔티 같은 검색어를 1개 추천해줘";
//
//        // 요청 JSON 구성
//        JsonObject json = new JsonObject();
//        json.addProperty("model", modelName);
//        json.addProperty("prompt", prompt);
//        json.addProperty("stream", false);
//
//        // HTTP 요청
//        URL url = new URL(ollamaBaseUrl + "/api/generate");
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setRequestMethod("POST");
//        conn.setDoOutput(true);
//        conn.setRequestProperty("Content-Type", "application/json");
//
//        try (OutputStream os = conn.getOutputStream()) {
//            os.write(json.toString().getBytes("utf-8"));
//        }
//
//        StringBuilder responseBuilder = new StringBuilder();
//        try (BufferedReader br = new BufferedReader(
//                new InputStreamReader(conn.getInputStream(), "utf-8"))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                responseBuilder.append(line.trim());
//            }
//        }
//
//        JsonObject result = JsonParser.parseString(responseBuilder.toString()).getAsJsonObject();
//
//        String recommend = result.get("response").getAsString(); // 추천 키워드 문자열 반환
//
//        return searchShopBO.getKeywordList(recommend);
//    }
//}
