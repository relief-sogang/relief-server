package com.sg.relief.domain.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
//@NoArgsConstructor
@Builder
public class LoginResponse {
    private Long id;
    private String name;
    private String email;
    private String accessToken;
    private String refreshToken;
}
