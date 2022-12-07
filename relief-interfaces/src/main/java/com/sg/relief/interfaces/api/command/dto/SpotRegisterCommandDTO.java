package com.sg.relief.interfaces.api.command.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SpotRegisterCommandDTO {
    private String userId;
    private String name;
    private String lat;
    private String lng;
}
