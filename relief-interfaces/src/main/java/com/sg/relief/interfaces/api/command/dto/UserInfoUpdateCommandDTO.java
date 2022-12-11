package com.sg.relief.interfaces.api.command.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoUpdateCommandDTO {
    private String userId;
    private String name;
    private String phoneNumber;
}
