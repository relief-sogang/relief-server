package com.sg.relief.interfaces.api.command.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GuardianRenameCommandDTO {
    private String userId;
    private String guardianId;
    private String rename;
}
