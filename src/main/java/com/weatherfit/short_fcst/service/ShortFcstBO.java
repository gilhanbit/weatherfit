package com.weatherfit.short_fcst.service;

import com.weatherfit.short_fcst.domain.ShortFcstDTO;
import com.weatherfit.short_fcst.entity.ShortFcstEntity;
import com.weatherfit.short_fcst.repository.ShortFcstRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShortFcstBO {

    private static final Logger log = LoggerFactory.getLogger(ShortFcstBO.class);

    private final ShortFcstRepository shortFcstRepository;
    private final WebClient webClient = WebClient.builder().build();

    @Value("${weather.short.url}")
    private String shortUrl;

    @Value("${weather.short.service-key}")
    private String serviceKey;

    public Mono<Void> setShortFcst(String baseDate, String baseTime, String nx, String ny) {
        return webClient.post()
                .uri(shortUrl)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .body(BodyInserters.fromFormData("serviceKey", serviceKey)
                        .with("pageNo", "1")
                        .with("numOfRows", "1000")
                        .with("dataType", "JSON")
                        .with("base_date", baseDate)
                        .with("base_time", baseTime)
                        .with("nx", nx)
                        .with("ny", ny))
                .retrieve()
                .bodyToMono(ShortFcstDTO.WeatherApiResponse.class)
                .doOnNext(apiResponse -> {
                    if (apiResponse != null
                            && apiResponse.getResponse() != null
                            && apiResponse.getResponse().getBody() != null
                            && apiResponse.getResponse().getBody().getItems() != null
                            && apiResponse.getResponse().getBody().getItems().getItem() != null) {

                        List<ShortFcstEntity> entities = apiResponse.getResponse().getBody().getItems().getItem()
                                .stream()
                                .map(item -> ShortFcstEntity.builder()
                                        .baseDate(item.getBaseDate())
                                        .baseTime(item.getBaseTime())
                                        .category(item.getCategory())
                                        .fcstDate(item.getFcstDate())
                                        .fcstTime(item.getFcstTime())
                                        .fcstValue(item.getFcstValue())
                                        .nx(item.getNx())
                                        .ny(item.getNy())
                                        .build())
                                .collect(Collectors.toList());

                        shortFcstRepository.saveAll(entities);
                        log.info("Saved {} forecasts to DB", entities.size());
                    } else {
                        log.warn("API response is null or 구조 이상");
                    }
                })
                .then();
    }

    public List<ShortFcstEntity> getStoredForecasts() {
        return shortFcstRepository.findAll();
    }
}
