package com.weatherfit.common.util;

import com.weatherfit.domain.NearTitleSamplingDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class NearSampling {

    public static List<String> nearTitleSampling(List<NearTitleSamplingDTO> list, int count) {
        double lambda = 0.05;
        LocalDate now = LocalDate.now();

        // 가중치
        List<Double> weights = list.stream()
                .map(item -> {
                    long days = ChronoUnit.DAYS.between(item.getCreatedAt(), now);
                    return Math.exp(-lambda * days);
                })
                .collect(Collectors.toList());

        List<String> sampled = new ArrayList<>();
        Random rand = new Random();

        // 가중치에 따라 랜덤 뽑기
        for (int i = 0; i < count && !list.isEmpty(); i++) {
            double total = weights.stream().mapToDouble(w -> w).sum();
            double r = rand.nextDouble() * total;

            double cumulative = 0;
            for (int j = 0; j < list.size(); j++) {
                cumulative += weights.get(j);
                if (r <= cumulative) {
                    sampled.add(list.get(j).getTitle());

                    // 뽑힌 값은 제거 (중복 방지)
                    list.remove(j);
                    weights = new ArrayList<>(weights);
                    weights.remove(j);
                    break;
                }
            }
        }

        return sampled;
    }
}
