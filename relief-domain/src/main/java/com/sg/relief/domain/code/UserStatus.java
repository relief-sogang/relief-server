package com.sg.relief.domain.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserStatus {
    CREATED("생성"),
    ON("ON"),
    OFF("OFF"),
    SHARING("위치 공유 상태"),
    COMPLETED( "완료");

    private final String description;
}
