package com.weatherfit;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.*;

@Slf4j
public class 정렬테스트 {

    @ToString
    @AllArgsConstructor
    class Data {
        int count;
    }

    @Test
    void test() {
        // given
        List<Data> list = new ArrayList<>(
                List.of(
                        new Data(3),
                        new Data(5),
                        new Data(2)
                ));
        log.info("as-is: {}", list);

        // when
        정렬_고전(list);
        정렬_람다(list); // 한줄일때 사용

        // then
        log.info("###### to-be: {}", list);
    }

    void 정렬_람다(List<Data> list) {
        Collections.sort(list, (d1, d2) -> Integer.compare(d1.count, d2.count));
    }

    void 정렬_고전(List<Data> list) {
        Collections.sort(list, new Comparator<>() {
            @Override
            public int compare(Data d1, Data d2) {
                // 앞의 값이 크면 1, 같으면 0, 아니면 -1
                return Integer.compare(d1.count, d2.count);
            }
        });
    }
}
