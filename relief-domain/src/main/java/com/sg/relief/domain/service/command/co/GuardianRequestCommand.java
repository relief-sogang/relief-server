package com.sg.relief.domain.service.command.co;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GuardianRequestCommand {
    private String protegeId;
    private String guardianId;
    private String guardianName;
    private String message;
}
