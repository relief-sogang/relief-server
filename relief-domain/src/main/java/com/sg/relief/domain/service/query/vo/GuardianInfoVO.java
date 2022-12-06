package com.sg.relief.domain.service.query.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GuardianInfoVO {
    private String name;
    private String id;
    private String email;
    private String status;
}
