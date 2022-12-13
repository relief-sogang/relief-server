package com.sg.relief.domain.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
//@NoArgsConstructor
@Builder
public class LoginResponse {
    private Long id;
    private String userId;
    private String name;
    private String phoneNumber;
    private String email;
    private String accessToken;
    private String refreshToken;
    private Boolean isNew;
}
