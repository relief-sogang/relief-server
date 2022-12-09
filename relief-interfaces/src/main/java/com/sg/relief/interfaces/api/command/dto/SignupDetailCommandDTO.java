package com.sg.relief.interfaces.api.command.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupDetailCommandDTO {
    private String email;
    private String userId;
    private String userName;
    private String password;
    private String phoneNumber;
}
