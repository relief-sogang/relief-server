package com.sg.relief.interfaces.api.command.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPushAlarmCommandDTO {
    private String userId;
    private String status;
}
