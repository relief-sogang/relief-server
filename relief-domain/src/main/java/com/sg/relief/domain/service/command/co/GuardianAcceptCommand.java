package com.sg.relief.domain.service.command.co;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GuardianAcceptCommand {
    private String userId;
    private String protegeId;
    private String protegeName;
    private Boolean isAccept;
}
