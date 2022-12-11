package com.sg.relief.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class PushNotificationRequest {
    private String title;
    private String message;
    private String topic;
    private String token;
}
