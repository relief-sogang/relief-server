package com.sg.relief.domain.service.command.co;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GuardianAcceptCommand {
    private String userId;
    private String protegeId;
    private String protegeName;
    private Boolean isAccept;
}
