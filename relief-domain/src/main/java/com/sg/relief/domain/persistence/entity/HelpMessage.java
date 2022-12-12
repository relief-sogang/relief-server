package com.sg.relief.domain.persistence.entity;

import com.sg.relief.domain.code.HelpMessageStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="help_message")
@Entity
@SuperBuilder
public class HelpMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String senderId;

    @Column
    private String receiverId;

    @Column
    private String dateTime;

    @Column
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HelpMessageStatus checkStatus;
}
