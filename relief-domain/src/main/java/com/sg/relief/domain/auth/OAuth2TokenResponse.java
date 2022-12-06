package com.sg.relief.domain.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
public class OAuth2TokenResponse {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("id_token")
    private String idToken;

    @JsonProperty("expires_in")
    private Date expiresIn;

    @JsonProperty("refresh_token_expires_in")
    private Date refreshTokenExpiresIn;

    public OAuth2TokenResponse(){

    }
}
