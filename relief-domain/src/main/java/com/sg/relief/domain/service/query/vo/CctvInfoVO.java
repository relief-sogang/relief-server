package com.sg.relief.domain.service.query.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CctvInfoVO {
    private Double xAxis;   // latitude
    private Double yAxis;   // longitude
}
