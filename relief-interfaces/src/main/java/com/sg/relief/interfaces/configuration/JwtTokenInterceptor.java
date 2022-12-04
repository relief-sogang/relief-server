package com.sg.relief.interfaces.configuration;

import com.sg.relief.domain.auth.jwt.JwtManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenInterceptor implements HandlerInterceptor {

    private final JwtManager jwtManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
    // controller 진입 전에 실행됨. 반환 값이 true일 경우 controller로 진입하고, false인 경우 진입하지 않음

        log.info("==== preHandle ====");
        String accessToken = request.getHeader("accessToken");
        log.info("accessToken: {}", accessToken);

//        String refreshToken = request.getHeader("REFRESH_TOKEN");
//        System.out.println("RefreshToken:" + refreshToken);

        if (accessToken != null && jwtManager.checkClaim(accessToken)) {
            log.info("==== TRUE ====");
            return true;
        }

        response.setStatus(401);
        response.setHeader("AccessToken", accessToken);
        response.setHeader("msg", "Check the tokens.");
        return false;
    }
}
