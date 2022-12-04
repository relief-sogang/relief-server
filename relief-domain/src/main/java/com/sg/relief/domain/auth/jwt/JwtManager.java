package com.sg.relief.domain.auth.jwt;

import com.sg.relief.domain.persistence.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.lang.reflect.Member;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtManager {
//    private final String securityKey = "testSecurityKey"; // TODO : 민감정보는 따로 분리

    @Value("${jwt.secret}")
    private String secret;

    private final Long expiredTime = 1000 * 60L * 60L * 3L; // 유효시간 3시간


    public String createJwt(Authentication authentication) {
        Date now = new Date();

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        log.info("{}==>OAuth2User", oAuth2User);

        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        header.put("regDate", System.currentTimeMillis());

        Map<String, Object> claims = new HashMap<>();
        claims.put("name", oAuth2User.getAttributes().get("name"));
        claims.put("email", oAuth2User.getAttributes().get("email"));

        log.info("{}=====>CLAIMS", claims);

        return Jwts.builder()
                .setSubject("user")
                .setHeader(header)
                .setClaims(claims)
                .setExpiration(new Date(now.getTime() + expiredTime))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    //TODO : 토큰 검증 부분 추가
}
