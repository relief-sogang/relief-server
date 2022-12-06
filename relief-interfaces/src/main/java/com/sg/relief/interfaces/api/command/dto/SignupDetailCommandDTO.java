package com.sg.relief.interfaces.api.command.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SignupDetailCommandDTO {
    private String id;
    private String password;
    private String phoneNumber;
}
