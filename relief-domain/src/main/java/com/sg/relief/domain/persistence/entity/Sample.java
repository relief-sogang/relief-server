package com.sg.relief.domain.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="sample")
@Entity
@SuperBuilder
public class Sample {
    @Id
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;
}
