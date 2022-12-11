package com.sg.relief.domain.service.query.vo;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProtegeInfoVO {
    private String id;
    private String name;
    private String email;
    private String status;
}
