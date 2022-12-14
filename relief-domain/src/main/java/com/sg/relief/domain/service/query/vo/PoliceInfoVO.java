package com.sg.relief.domain.service.query.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PoliceInfoVO {
    private String name;
    private String address;
    private String lat;
    private String lng;
}
