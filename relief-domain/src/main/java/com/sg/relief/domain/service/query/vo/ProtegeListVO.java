package com.sg.relief.domain.service.query.vo;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProtegeListVO {
    private List<ProtegeInfoVO> protegeList;
}
