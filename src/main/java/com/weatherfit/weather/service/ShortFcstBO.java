package com.weatherfit.weather.service;

import com.weatherfit.weather.domain.ShortFcst;
import com.weatherfit.weather.mapper.ShortFcstMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShortFcstBO {

    private final ShortFcstMapper shortFcstMapper;

    public ShortFcst getTodayFcst(String todayFcst) {
        return shortFcstMapper.selectTodayFcst(todayFcst);
    }

    // 클라 위치에 맞는 서버 데이터 확인
    public List<ShortFcst> getShortFcstlist(String x, String y) {
        List<ShortFcst> reverseShortFcst = shortFcstMapper.getShortFcstlist(x, y);
        Collections.reverse(reverseShortFcst);
        return reverseShortFcst;
    }

    // 기상청 응답 -> 내 서버 저장
    public void setShortFcst(String result) {
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
        int timeNow = Integer.parseInt(time.format(timeFormatter));

        JSONParser jsonParser = new JSONParser();

        try {
            JSONObject jsonObj = (JSONObject) jsonParser.parse(result);
            JSONObject parse_response = (JSONObject) jsonObj.get("response");
            JSONObject parse_body = (JSONObject) parse_response.get("body");
            JSONObject parse_items = (JSONObject) parse_body.get("items");
            JSONArray parse_item = (JSONArray) parse_items.get("item"); // response > body > items > item

            Long totalCount = (Long) parse_body.get("totalCount");

            // 중복되는 날짜 없도록 더블 체크 (추후 로직 수정)
            LinkedHashMap<String, ShortFcst> fcstMap = new LinkedHashMap<>();

            for (int i = 0; i < totalCount; i++) {
                JSONObject obj = (JSONObject) parse_item.get(i);
                String category = (String) obj.get("category");
                String fcstTimeStr = (String) obj.get("fcstTime");
                int fcstTime = Integer.parseInt(fcstTimeStr);

                // 카테고리에서 기온 (최저, 최고) 필터링
                if ("TMN".equals(category) || "TMX".equals(category) || "TMP".equals(category)) {
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

                    // 예보일자 존재 확인
                    Boolean isFind = shortFcstMapper.findFcstDate(fcstDate);

                    if (isFind != null) {
                        ShortFcst shortFcst = fcstMap.getOrDefault(fcstDate, new ShortFcst());
                        shortFcst.setFcstDate(fcstDate);
                        shortFcst.setNx(nx);
                        shortFcst.setNy(ny);

                        if ("TMN".equals(category)) {
                            shortFcst.setTmn(fcstValue);
                        } else if ("TMX".equals(category)) {
                            shortFcst.setTmx(fcstValue);
                        } else if ("TMP".equals(category)) {
                            int diff = Math.abs(fcstTime - timeNow);

                            // 기존 TMP와 diff 비교
                            if (shortFcst.getTmp() == null || shortFcst.getTmpDiff() == null || diff < shortFcst.getTmpDiff()) {
                                shortFcst.setTmp(fcstValue);
                                shortFcst.setTmpDiff(diff);
                            }
                        }

                        fcstMap.put(fcstDate, shortFcst);

                        if (shortFcst.getTmn() != null && shortFcst.getTmx() != null && shortFcst.getTmp() != null) {
                            shortFcstMapper.updateShortFcst(shortFcst);
                        }
                    } else {
                        ShortFcst shortFcst = fcstMap.getOrDefault(fcstDate, new ShortFcst());
                        shortFcst.setFcstDate(fcstDate);
                        shortFcst.setNx(nx);
                        shortFcst.setNy(ny);

                        if ("TMN".equals(category)) {
                            shortFcst.setTmn(fcstValue);
                        } else if ("TMX".equals(category)) {
                            shortFcst.setTmx(fcstValue);
                        } else if ("TMP".equals(category)) {
                            int diff = Math.abs(fcstTime - timeNow);

                            if (shortFcst.getTmp() == null || shortFcst.getTmpDiff() == null || diff < shortFcst.getTmpDiff()) {
                                shortFcst.setTmp(fcstValue);
                                shortFcst.setTmpDiff(diff);
                            }
                        }

                        fcstMap.put(fcstDate, shortFcst);

                        if (shortFcst.getTmn() != null && shortFcst.getTmx() != null && shortFcst.getTmp() != null) {
                            shortFcstMapper.insertShortFcst(shortFcst);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
