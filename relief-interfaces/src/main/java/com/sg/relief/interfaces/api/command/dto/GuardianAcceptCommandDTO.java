package com.sg.relief.interfaces.api.command.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GuardianAcceptCommandDTO {
    private String userId;
    private String protegeId;
    private String protegeName;
    private Boolean isAccept;
}
