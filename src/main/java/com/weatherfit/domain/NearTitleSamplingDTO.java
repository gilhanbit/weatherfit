package com.weatherfit.domain;

import java.time.LocalDate;

public class NearTitleSamplingDTO {
    private String title;
    private LocalDate createdAt;

    public NearTitleSamplingDTO(String title, LocalDate createdAt) {
        this.title = title;
        this.createdAt = createdAt;
    }

    public String getTitle() {
        return title;
    }
    public LocalDate getCreatedAt() {
        return createdAt;
    }
}
