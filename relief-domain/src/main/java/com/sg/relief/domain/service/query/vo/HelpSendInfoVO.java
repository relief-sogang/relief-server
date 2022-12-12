package com.sg.relief.domain.service.query.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class HelpSendInfoVO {
    private String guardianName;
    private String date;
    private String message;
}
