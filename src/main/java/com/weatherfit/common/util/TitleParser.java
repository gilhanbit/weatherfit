//package com.weatherfit.common.util;
//
//public class TitleParser {
//    public static List keywordFrequency(String[] args) {
//        String title = null;
//
//
//        for (String raw : title) {
//            String noTags = raw.replaceAll("<[^>]+>", ""); // 태그 제거
//            String[] words = noTags.split("\\s+"); // 단어 분리
//
//            for (String word : words) {
//                word = word.trim();
//                if (word.isEmpty()) continue;
//                freqMap.put(word, freqMap.getOrDefault(word, 0) + 1);
//            }
//        }
//
//
//    }
//}
