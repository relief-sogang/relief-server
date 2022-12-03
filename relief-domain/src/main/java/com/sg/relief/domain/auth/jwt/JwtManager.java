package com.sg.relief.domain.auth.jwt;

import com.sg.relief.domain.persistence.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;

import java.lang.reflect.Member;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtManager {
    private final String securityKey = "testSecurityKey"; // TODO : 민감정보는 따로 분리
    private final Long expiredTime = 1000 * 60L * 60L * 3L; // 유효시간 3시간


    public String createJwt(Authentication authentication) {
        Date now = new Date();

        DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject("user") // 토큰 용도
                .setHeader(createHeader())
                .setClaims(createClaims(user))
                .setExpiration(new Date(now.getTime() + expiredTime))
                .signWith(SignatureAlgorithm.HS256, securityKey)
                .compact();
    }

    private Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", "HS256"); // 해시 256 사용하여 암호화
        header.put("regDate", System.currentTimeMillis());
        return header;
    }

    private Map<String, Object> createClaims(DefaultOAuth2User defaultOAuth2User) {
        Map<String, Object> claims = new HashMap<>();
//        claims.put("username", defaultOAuth2User.getAttributes());
//        claims.put("roles", user.getRole());
        return claims;
    }

    //TODO : 토큰 검증 부분 추가
}
