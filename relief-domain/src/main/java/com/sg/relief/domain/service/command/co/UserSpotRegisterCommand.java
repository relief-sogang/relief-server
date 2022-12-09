package com.sg.relief.domain.service.command.co;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserSpotRegisterCommand {
    private String userId;
    private String name;
    private String lat;
    private String lng;
}
