package com.sg.relief.domain.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HelpMessageStatus {
    Y("Y"),
    N("N");
    private final String description;
}
