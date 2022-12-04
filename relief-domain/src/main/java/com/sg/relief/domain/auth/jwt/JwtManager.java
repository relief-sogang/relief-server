package com.sg.relief.domain.auth.jwt;

import com.sg.relief.domain.persistence.entity.User;
import io.jsonwebtoken.*;
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

    @Value("${jwt.secret}")
    private String secretKey;

    private final Long accessTokenExpiredTime = 1000 * 60L * 60L * 3L; // 유효시간 3시간
    private final Long refreshTokenExpiredTime = 1000 * 60L * 60L * 24L * 14L; // 유효시간 14일


    public Token createJwt(Authentication authentication) {
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

        String accessToken = Jwts.builder()
                .setSubject("user")
                .setHeader(header)
                .setClaims(claims)
                .setExpiration(new Date(now.getTime() + accessTokenExpiredTime))
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();

        String refreshToken = Jwts.builder()
                .setSubject("user")
                .setHeader(header)
                .setClaims(claims)
                .setExpiration(new Date(now.getTime() + refreshTokenExpiredTime))
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();

        return Token.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public boolean checkClaim(String jwt){
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey.getBytes())
                    .parseClaimsJws(jwt).getBody();
            return true;

        }catch(ExpiredJwtException e) {
            log.error("Token Expired");
            return false;

        }catch(JwtException e) {
            log.error("Token Error");
            return false;
        }
    }

    public Claims getJwtContents(String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey.getBytes())
                .parseClaimsJws(jwt).getBody();
        return claims;
    }
}
