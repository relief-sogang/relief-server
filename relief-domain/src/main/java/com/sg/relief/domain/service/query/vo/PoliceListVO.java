package com.sg.relief.domain.service.query.vo;

import com.sg.relief.domain.persistence.entity.Police;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class PoliceListVO {
    private List<PoliceInfoVO> policeList;
}
