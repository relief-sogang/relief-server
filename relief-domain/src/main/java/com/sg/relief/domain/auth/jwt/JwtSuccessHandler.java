package com.sg.relief.domain.auth.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sg.relief.domain.auth.jwt.JwtManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtManager jwtManager;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        try( PrintWriter writer = response.getWriter()) {
            log.info("Authentication : {}", authentication );
            Token jwt = jwtManager.createJwt(authentication);
            log.info("Jwt : {}", jwt );
            JSONObject json = new JSONObject();
            json.appendField("accessToken",jwt.getAccessToken());
            json.appendField("refreshToken",jwt.getRefreshToken());

            response.setStatus(HttpStatus.ACCEPTED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());

            writer.write(json.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
