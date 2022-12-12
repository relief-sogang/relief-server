package com.sg.relief.domain.service.query.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class HelpReceiveInfoVO {
    private String messageId;
    private String protegeName;
    private String date;
    private String message;
    private String checkStatus;
}
