package com.weatherfit.common;

public class OutfitByTemp {

    public static String topByTemp(Double tempFcst) {
        int temp = (int) Math.round(tempFcst);

        if (temp >= 24) {
            return "반팔";
        } else if (temp >= 20) {
            return "긴팔티";
        } else if (temp >= 17) {
            return "맨투맨";
        } else if (temp >= 11) {
            return "자켓";
        } else if (temp >= 6) {
            return "자켓";
        } else {
            return "패딩";
        }
    }


    public static String bottomByTemp(Double tempFcst) {
        int temp = (int) Math.round(tempFcst);

        if (temp >= 24) {
            return "반바지";
        } else if (temp >= 6) {
            return "긴바지";
        } else {
            return "기모바지";
        }
    }


    public static String shoesByTemp(Double tempFcst) {
        int temp = (int) Math.round(tempFcst);

        if (temp >= 24) {
            return "여름운동화";
        } else if (temp >= 6) {
            return "가을운동화";
        } else {
            return "겨울운동화";
        }
    }
}
