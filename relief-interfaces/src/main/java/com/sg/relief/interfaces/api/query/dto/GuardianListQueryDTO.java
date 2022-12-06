package com.sg.relief.interfaces.api.query.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class GuardianListQueryDTO {
    private String userId;
}
