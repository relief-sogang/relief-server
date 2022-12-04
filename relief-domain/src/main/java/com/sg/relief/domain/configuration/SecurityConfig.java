package com.sg.relief.domain.configuration;

import com.sg.relief.domain.auth.jwt.JwtSuccessHandler;
import com.sg.relief.domain.auth.CustomOAuth2UserService;
import com.sg.relief.domain.auth.code.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Slf4j
@Configuration
@EnableWebSecurity
@PropertySource("classpath:application-oauth.properties")
public class SecurityConfig {

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    private JwtSuccessHandler jwtSuccessHandler;

    @Autowired
    private Environment env;


    private static String CLIENT_PROPERTY_KEY= "spring.security.oauth2.client.registration";


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .authorizeRequests() // URL 별 권한 접근제어 관리 시작점
//                .antMatchers(HttpMethod.OPTIONS).permitAll()
                    .antMatchers("/login/**", "/oauth2/**").permitAll()
                    .antMatchers("/", "/css/**", "/images/**", "/js/**") // 권한 관리 대상
                    .permitAll() // 모든 권한에게 공개
                    .antMatchers("/api/**") // 권한 관리 대상
                    .hasRole(Role.USER.name()) // User 권한에게만 공개
                    .anyRequest().authenticated() // 나머지 요청은 인증된 사용자(로그인) 에게만 공개
                .and()
//                .addFilterBefore()
                    .oauth2Login() // oauth2 로그인 설정의 진입점
                    .userInfoEndpoint() // 로그인 성공 이후 사용자 정보 가져올 때의 설정
                    .userService(customOAuth2UserService) // 로그인 성공 시 후속조치를 진행할 UserService의 구현체 등록
                .and()
                    .defaultSuccessUrl("/test")
                    .successHandler(jwtSuccessHandler) // 로그인 성공 후 핸들러
                    .failureHandler(null) // 로그인 실패 시 핸들러
                .and()
                    .logout().logoutSuccessUrl("/")
                    .invalidateHttpSession(true) // 로그아웃시 세션 제거
                    .deleteCookies("JSESSIONID") // 쿠키제거
                    .clearAuthentication(true) // 권한정보 제거
        ;

        return http.build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        log.info("====corsConfigurationSource====");
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOriginPattern("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(this.googleClientRegistration());
    }

    private ClientRegistration googleClientRegistration() {
        return ClientRegistration.withRegistrationId("google")
                .clientId(env.getProperty(CLIENT_PROPERTY_KEY + ".google.client-id"))
                .clientSecret(env.getProperty(CLIENT_PROPERTY_KEY + ".google.client-secret"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
                .scope("openid", "profile", "email")
                .authorizationUri("https://accounts.google.com/o/oauth2/v2/auth")
                .tokenUri("https://www.googleapis.com/oauth2/v4/token")
                .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
                .userNameAttributeName(IdTokenClaimNames.SUB)
                .jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
                .clientName("Google")
                .build();
    }
//
//    private ClientRegistration kakaoClientRegistration() {
//        return ClientRegistration.withRegistrationId("kakao")
//                .clientId("kakao-client-id")
//                .clientSecret("kakao-client-secret")
//                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
//                .scope("profile")
//                .authorizationUri("https://kauth.kakao.com/oauth/authorize")
//                .tokenUri("https://kauth.kakao.com/oauth/token")
//                .userInfoUri("https://kapi.kakao.com/v2/user/me")
//                .userNameAttributeName(IdTokenClaimNames.SUB)
////                .jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
//                .clientName("Kakao")
//                .build();
//    }

}
