package com.sg.relief.domain.service.query.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class HelpSendListVO {
    private List <HelpSendInfoVO> sendList;
}
