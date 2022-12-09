package com.sg.relief.interfaces.api.command.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SpotRegisterCommandDTO {
    private String userId;
    private String name;
    private String lat;
    private String lng;
}
