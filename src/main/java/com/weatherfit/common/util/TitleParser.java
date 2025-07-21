package com.weatherfit.common.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TitleParser {

    public static List<String> keywordFrequency(List<String> T) {

        Map<String, Integer> freqMap = new HashMap<>();

        for (String raw : T) {
            String noTags = raw.replaceAll("<[^>]+>", "");
            String[] words = noTags.split("\\s+");

            for (String word : words) {
                word = word.trim();
                if (word.isEmpty()) continue;

                freqMap.put(word, freqMap.getOrDefault(word, 0) + 1);
            }
        }

        List<String> sortedKeywords = freqMap.entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return sortedKeywords;
    }
}
