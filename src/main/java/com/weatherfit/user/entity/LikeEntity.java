package com.weatherfit.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "`like`")
@Entity
public class LikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int userId;
    private String link;
    private String image;
    private String title;
    private int lprice;
    private String category1;
    private String category2;
    private String category3;
    @CreationTimestamp
    private LocalDateTime createdAt;

}
