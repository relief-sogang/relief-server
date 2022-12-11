package com.sg.relief.interfaces.api.query.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProtegeListQueryDTO {
    private String userId;
    private String status;
}
