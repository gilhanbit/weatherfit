package com.weatherfit.user.service;

import com.weatherfit.user.domain.Style;
import com.weatherfit.user.mapper.StyleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StyleBO {

    private final StyleMapper styleMapper;

    /**
     * keyword chart by gender & age
     * @param gender
     * @param age
     * @return
     */
    public LinkedHashMap<String, Double> getTopChartByGA(String gender, int age) {
        age = age / 10;
        List<String> topResult = styleMapper.selectTopChartByGA(gender, age);

        // 1. "미설정"이나 null 제거
        List<String> filteredList = topResult.stream()
                .filter(s -> s != null && !"미설정".equals(s.trim()))
                .collect(Collectors.toList());

        // 2. 카운팅
        Map<String, Long> countMap = filteredList.stream()
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()));

        // 3. 내림차순 정렬
        List<Map.Entry<String, Long>> sortedList = countMap.entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .collect(Collectors.toList());

        // 4. 총합
        long total = sortedList.stream()
                .mapToLong(Map.Entry::getValue)
                .sum();

        // 5. 상위 3개만 비율 계산 후 저장
        LinkedHashMap<String, Double> percentMap = new LinkedHashMap<>();
        sortedList.stream()
                .limit(3)
                .forEach(entry -> {
                    double percent = (entry.getValue() * 100.0) / total;
                    percentMap.put(entry.getKey(), Math.round(percent * 10) / 10.0);
                });

        return percentMap;
    }

    public LinkedHashMap<String, Double> getBottomChartByGA(String gender, int age) {
        age = age / 10;
        List<String> bottomResult = styleMapper.selectBottomChartByGA(gender, age);

        // 1. "미설정"이나 null 제거
        List<String> filteredList = bottomResult.stream()
                .filter(s -> s != null && !"미설정".equals(s.trim()))
                .collect(Collectors.toList());

        // 2. 카운팅
        Map<String, Long> countMap = filteredList.stream()
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()));

        // 3. 내림차순 정렬
        List<Map.Entry<String, Long>> sortedList = countMap.entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .collect(Collectors.toList());

        // 4. 총합
        long total = sortedList.stream()
                .mapToLong(Map.Entry::getValue)
                .sum();

        // 5. 상위 3개만 비율 계산 후 저장
        LinkedHashMap<String, Double> percentMap = new LinkedHashMap<>();
        sortedList.stream()
                .limit(3)
                .forEach(entry -> {
                    double percent = (entry.getValue() * 100.0) / total;
                    percentMap.put(entry.getKey(), Math.round(percent * 10) / 10.0);
                });

        return percentMap;
    }

    public LinkedHashMap<String, Double> getShoesChartByGA(String gender, int age) {
        age = age / 10;
        List<String> shoesResult = styleMapper.selectShoesChartByGA(gender, age);

        // 1. "미설정"이나 null 제거
        List<String> filteredList = shoesResult.stream()
                .filter(s -> s != null && !"미설정".equals(s.trim()))
                .collect(Collectors.toList());

        // 2. 카운팅
        Map<String, Long> countMap = filteredList.stream()
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()));

        // 3. 내림차순 정렬
        List<Map.Entry<String, Long>> sortedList = countMap.entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .collect(Collectors.toList());

        // 4. 총합
        long total = sortedList.stream()
                .mapToLong(Map.Entry::getValue)
                .sum();

        // 5. 상위 3개만 비율 계산 후 저장
        LinkedHashMap<String, Double> percentMap = new LinkedHashMap<>();
        sortedList.stream()
                .limit(3)
                .forEach(entry -> {
                    double percent = (entry.getValue() * 100.0) / total;
                    percentMap.put(entry.getKey(), Math.round(percent * 10) / 10.0);
                });

        return percentMap;
    }


    /**
     * keyword chart by location
     * @param x
     * @param y
     * @return
     */
    public LinkedHashMap<String, Double> getTopChartByLocation(int x, int y) {
        List<String> topResult = styleMapper.selectTopChartByLocation(x, y);

        // 1. "미설정"이나 null 제거
        List<String> filteredList = topResult.stream()
                .filter(s -> s != null && !"미설정".equals(s.trim()))
                .collect(Collectors.toList());

        // 2. 카운팅
        Map<String, Long> countMap = filteredList.stream()
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()));

        // 3. 내림차순 정렬
        List<Map.Entry<String, Long>> sortedList = countMap.entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .collect(Collectors.toList());

        // 4. 총합
        long total = sortedList.stream()
                .mapToLong(Map.Entry::getValue)
                .sum();

        // 5. 상위 3개만 비율 계산 후 저장
        LinkedHashMap<String, Double> percentMap = new LinkedHashMap<>();
        sortedList.stream()
                .limit(3)
                .forEach(entry -> {
                    double percent = (entry.getValue() * 100.0) / total;
                    percentMap.put(entry.getKey(), Math.round(percent * 10) / 10.0);
                });

        return percentMap;
    }

    public LinkedHashMap<String, Double> getBottomChartByLocation(int x, int y) {
        List<String> bottomResult = styleMapper.selectBottomChartByLocation(x, y);

        // 1. "미설정"이나 null 제거
        List<String> filteredList = bottomResult.stream()
                .filter(s -> s != null && !"미설정".equals(s.trim()))
                .collect(Collectors.toList());

        // 2. 카운팅
        Map<String, Long> countMap = filteredList.stream()
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()));

        // 3. 내림차순 정렬
        List<Map.Entry<String, Long>> sortedList = countMap.entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .collect(Collectors.toList());

        // 4. 총합
        long total = sortedList.stream()
                .mapToLong(Map.Entry::getValue)
                .sum();

        // 5. 상위 3개만 비율 계산 후 저장
        LinkedHashMap<String, Double> percentMap = new LinkedHashMap<>();
        sortedList.stream()
                .limit(3)
                .forEach(entry -> {
                    double percent = (entry.getValue() * 100.0) / total;
                    percentMap.put(entry.getKey(), Math.round(percent * 10) / 10.0);
                });

        return percentMap;
    }

    public LinkedHashMap<String, Double> getShoesChartByLocation(int x, int y) {
        List<String> shoesResult = styleMapper.selectShoesChartByLocation(x, y);

        // 1. "미설정"이나 null 제거
        List<String> filteredList = shoesResult.stream()
                .filter(s -> s != null && !"미설정".equals(s.trim()))
                .collect(Collectors.toList());

        // 2. 카운팅
        Map<String, Long> countMap = filteredList.stream()
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()));

        // 3. 내림차순 정렬
        List<Map.Entry<String, Long>> sortedList = countMap.entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .collect(Collectors.toList());

        // 4. 총합
        long total = sortedList.stream()
                .mapToLong(Map.Entry::getValue)
                .sum();

        // 5. 상위 3개만 비율 계산 후 저장
        LinkedHashMap<String, Double> percentMap = new LinkedHashMap<>();
        sortedList.stream()
                .limit(3)
                .forEach(entry -> {
                    double percent = (entry.getValue() * 100.0) / total;
                    percentMap.put(entry.getKey(), Math.round(percent * 10) / 10.0);
                });

        return percentMap;
    }


    /**
     *
     * @param userId
     * @return
     */
    public Style getUserStyle(Integer userId) {
        return styleMapper.selectUserStyle(userId);
    }

    public boolean setUserStyle(int userId, String top, String bottom, String shoes) {
        return styleMapper.insertUserStyle(userId, top, bottom, shoes);
    }

    public boolean updateUserStyle(int userId, String top, String bottom, String shoes) {
        return styleMapper.updateUserStyle(userId, top, bottom, shoes);
    }
}
