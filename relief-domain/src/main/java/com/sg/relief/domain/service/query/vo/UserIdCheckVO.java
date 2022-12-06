package com.sg.relief.domain.service.query.vo;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserIdCheckVO {
    private Boolean isExist;
}
