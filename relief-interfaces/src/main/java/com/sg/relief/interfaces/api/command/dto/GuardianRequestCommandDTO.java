package com.sg.relief.interfaces.api.command.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GuardianRequestCommandDTO {
    private String userId;
    private String guardianId;
    private String guardianName;
    private String message;
}
