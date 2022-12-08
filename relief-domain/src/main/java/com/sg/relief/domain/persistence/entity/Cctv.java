package com.sg.relief.domain.persistence.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

/* 방범용, 다목적용 cctv, 위도와 경도만 저장. */
@NoArgsConstructor
@Getter
@Setter
@Table(name="cctv")
@Entity
public class Cctv {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Double lat; // 위도
    @Column(nullable = false)
    private Double lng; // 경도

    @Builder
    public Cctv(Long id, Double lat, Double lng) {
        this.id =id;
        this.lat = lat;
        this.lng = lng;
    }
}
