package com.sg.relief.domain.auth.user;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo {
    private Map<String, Object> attributes;

    public KakaoUserInfo(Map<String, Object> attributes) { this.attributes = attributes; }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getProviderId() { return String.valueOf(attributes.get("id")); }

    @Override
    public String getProvider() { return "kakao"; }

    @Override
    public String getEmail() { return (String) getKakaoAccount().get("email");}

    @Override
    public String getName() {
        return (String) getKakaoAccount().get("nickname");
    }

    public Map<String, Object> getKakaoAccount(){
        return(Map<String, Object>) attributes.get("kakao_account");
    }

}
