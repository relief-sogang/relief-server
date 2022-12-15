package com.sg.relief.interfaces.api.query.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PoliceListQueryDTO {
    private String lat;
    private String lng;
    private String userId;
}
