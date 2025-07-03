package com.weatherfit;

import com.weatherfit.short_fcst.domain.ShortFcst;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class 단기예보호출테스트 {
    public static void main(String[] args) throws IOException, ParseException {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String today = now.format(formatter);

        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=GDdYXnnTxEuSNBtekaoYDFXSmat2FNd0rJKDwFHcm9xwCg17tTDpdItmvPaz00%2BQnOiG6pmnPPuXIi74z%2B3bbA%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(today, "UTF-8")); /*‘21년 6월 28일발표*/
        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode("0200", "UTF-8")); /*05시 발표*/
        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode("61", "UTF-8")); /*예보지점의 X 좌표값*/
        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode("126", "UTF-8")); /*예보지점의 Y 좌표값*/

        String result = null;

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        result = sb.toString();
//        System.out.println(result);


        // BO
        JSONParser jsonParser = new JSONParser();

        JSONObject jsonObj = (JSONObject) jsonParser.parse(result);
        JSONObject parse_response = (JSONObject) jsonObj.get("response");
        JSONObject parse_body = (JSONObject) parse_response.get("body");
        JSONObject parse_items = (JSONObject) parse_body.get("items");
        JSONArray parse_item = (JSONArray) parse_items.get("item"); // response > body > items > item

//        for (int i = 0; i < parse_item.size(); i++) {
//            System.out.println(parse_item.get(i));
//        }

        Long totalCount = (Long) parse_body.get("totalCount");

        LinkedHashMap<String, ShortFcst> fcstMap = new LinkedHashMap<>();

        for (int i = 0; i < totalCount; i++) {
            JSONObject obj = (JSONObject) parse_item.get(i);
            String category = (String) obj.get("category");

            if ("TMN".equals(category) || "TMX".equals(category)) {
                String fcstDate = (String) obj.get("fcstDate");
                String fcstValueStr = (String) obj.get("fcstValue");
                Double fcstValue = null;
                Long nx = (Long) obj.get("nx");
                Long ny = (Long) obj.get("ny");

                try {
                    fcstValue = Double.parseDouble(fcstValueStr);
                } catch (NumberFormatException e) {
                    continue;
                }

                ShortFcst shortFcst = fcstMap.getOrDefault(fcstDate, new ShortFcst());
                shortFcst.setFcstDate(fcstDate);
                shortFcst.setNx(nx);
                shortFcst.setNy(ny);

                if ("TMN".equals(category)) {
                    shortFcst.setTmn(fcstValue);
                } else if ("TMX".equals(category)) {
                    shortFcst.setTmx(fcstValue);
                }

                fcstMap.put(fcstDate, shortFcst);

//                if (shortFcst.getTmn() != null && shortFcst.getTmx() != null) {
//                    shortFcstMapper.insertShortFcst(shortFcst);
//                }

                if (shortFcst.getTmn() != null && shortFcst.getTmx() != null) {
                    System.out.println(shortFcst.getFcstDate() + " " + shortFcst.getTmn() + " " + shortFcst.getTmx());
                }

            }
        }
    }
}
