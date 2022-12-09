package com.sg.relief.domain.persistence.entity;

import com.sg.relief.domain.code.UserMappingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="user_spot")
@Entity
@SuperBuilder
public class UserSpot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String userId;

    @Column
    private String spotName;

    @Column
    private BigDecimal lat; // 위도

    @Column
    private BigDecimal lng; // 경도

}
