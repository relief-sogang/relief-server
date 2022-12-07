package com.sg.relief.interfaces.api.command.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GuardianChangeStatusCommandDTO {
    private String userId;
    private String guardianId;
    private Boolean isActive;
}
