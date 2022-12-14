package com.sg.relief.domain.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="police")
@Entity
@SuperBuilder
public class Police {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name; // 치안센터명
    @Column
    private String address; // 주소
    @Column(nullable = false)
    private String lat; // y
    @Column(nullable = false)
    private String lng; // x
}
