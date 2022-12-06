package com.sg.relief.domain.persistence.entity;

import com.sg.relief.domain.code.UserMappingStatus;
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
@Table(name="user_mapping")
@Entity
@SuperBuilder
public class UserMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String guardianId;

    @Column
    private String protegeId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserMappingStatus status;

    @Column
    private String guardianName;

    @Column
    private String protegeName;

    @Column
    private String message;
}
