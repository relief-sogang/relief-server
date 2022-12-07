package com.sg.relief.interfaces.api.command.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GuardianAcceptCommandDTO {
    private String userId;
    private String protegeId;
    private String protegeName;
    private Boolean isAccept;
}
