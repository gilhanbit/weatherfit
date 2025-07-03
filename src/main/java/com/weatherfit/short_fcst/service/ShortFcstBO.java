package com.weatherfit.short_fcst.service;

import com.weatherfit.short_fcst.domain.ShortFcst;
import com.weatherfit.short_fcst.mapper.ShortFcstMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShortFcstBO {

    private final ShortFcstMapper shortFcstMapper;

    // 기상청 응답 -> 내 서버 저장
    public void setShortFcst(String result) {
        JSONParser jsonParser = new JSONParser();
        try {
            JSONObject jsonObj = (JSONObject) jsonParser.parse(result);
            JSONObject parse_response = (JSONObject) jsonObj.get("response");
            JSONObject parse_body = (JSONObject) parse_response.get("body");
            JSONObject parse_items = (JSONObject) parse_body.get("items");
            JSONArray parse_item = (JSONArray) parse_items.get("item"); // response > body > items > item

            Long totalCount = (Long) parse_body.get("totalCount");

            LinkedHashMap<String, ShortFcst> fcstMap = new LinkedHashMap<>();

            for (int i = 0; i < totalCount; i++) {
                JSONObject obj = (JSONObject) parse_item.get(i);
                String category = (String) obj.get("category");

                // 카테고리에서 기온 (최저, 최고) 필터링
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
                        }

                        fcstMap.put(fcstDate, shortFcst);

                        if (shortFcst.getTmn() != null && shortFcst.getTmx() != null) {
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
                        }

                        fcstMap.put(fcstDate, shortFcst);

                        if (shortFcst.getTmn() != null && shortFcst.getTmx() != null) {
                            shortFcstMapper.insertShortFcst(shortFcst);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 내 서버에서 호출
}
