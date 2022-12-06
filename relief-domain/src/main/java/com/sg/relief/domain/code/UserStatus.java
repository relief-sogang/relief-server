package com.sg.relief.domain.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserStatus {
    CREATED("생성"),
    COMPLETED( "완료");

    private final String description;
}
