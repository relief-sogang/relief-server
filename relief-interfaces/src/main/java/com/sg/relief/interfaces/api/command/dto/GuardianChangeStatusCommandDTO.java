package com.sg.relief.interfaces.api.command.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GuardianChangeStatusCommandDTO {
    private String userId;
    private String guardianId;
    private Boolean isActive;
}
