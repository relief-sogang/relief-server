package com.sg.relief.domain.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserMappingStatus {
    REQUEST("신청"),
    ON( "ON"),
    OFF( "OFF"),
    REJECT("거절");

    private final String description;
}
