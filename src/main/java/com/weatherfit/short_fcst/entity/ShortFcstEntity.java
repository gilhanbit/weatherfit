package com.weatherfit.short_fcst.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "short_fcst")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShortFcstEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String baseDate;
    private String baseTime;
    private String category;
    private String fcstDate;
    private String fcstTime;
    private String fcstValue;
    private int nx;
    private int ny;

    @CreationTimestamp
    private String createdAt;
}
