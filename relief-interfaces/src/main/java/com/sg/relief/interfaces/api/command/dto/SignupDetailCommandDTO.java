package com.sg.relief.interfaces.api.command.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SignupDetailCommandDTO {
    private String email;
    private String userId;
    private String userName;
    private String password;
    private String phoneNumber;
}
