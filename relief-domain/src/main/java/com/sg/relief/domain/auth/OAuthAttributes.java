package com.sg.relief.domain.auth;

import com.sg.relief.domain.auth.code.Role;
import com.sg.relief.domain.persistence.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;

    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if (registrationId.equals("kakao")) {
            return ofKakao(userNameAttributeName, attributes);
        } else {
            return ofGoogle(userNameAttributeName, attributes);
        }
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> kakao_account = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakao_account.get("profile");

        return new OAuthAttributes(attributes,
                userNameAttributeName,
                (String) profile.get("nickname"),
                (String) kakao_account.get("email"));
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {

        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();

//        return new OAuthAttributes(attributes,
//                userNameAttributeName,
//                (String) attributes.get("name"),
//                (String) attributes.get("email"));
    }

    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .role(Role.USER)
                .build();
    }
}
