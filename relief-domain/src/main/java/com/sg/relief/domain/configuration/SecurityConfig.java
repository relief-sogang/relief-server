package com.sg.relief.domain.configuration;

//import com.sg.relief.domain.auth.jwt.JwtSuccessHandler;
//import com.sg.relief.domain.auth.service.CustomOAuth2UserService;
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

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Configuration
@EnableWebSecurity
@PropertySource("classpath:application-oauth.properties")
public class SecurityConfig {

//    @Autowired
//    private CustomOAuth2UserService customOAuth2UserService;

//    @Autowired
//    private JwtSuccessHandler jwtSuccessHandler;

    @Autowired
    private Environment env;


    private static String CLIENT_PROPERTY_KEY= "spring.security.oauth2.client.registration";


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("filterChain: {}", http);
        http
                .csrf().disable()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .authorizeRequests() // URL ??? ?????? ???????????? ?????? ?????????
                    .antMatchers("/login/**", "/oauth2/**").permitAll()
                    .antMatchers("/", "/css/**", "/images/**", "/js/**") // ?????? ?????? ??????
                    .permitAll() // ?????? ???????????? ??????
                    .antMatchers("/api/**") // ?????? ?????? ??????
                .permitAll()
//                    .hasRole(Role.USER.name()) // User ??????????????? ??????
                    .anyRequest().authenticated() // ????????? ????????? ????????? ?????????(?????????) ????????? ??????
                .and()
                    .logout().logoutSuccessUrl("/")
//                    .invalidateHttpSession(true) // ??????????????? ?????? ??????
//                    .deleteCookies("JSESSIONID") // ????????????
//                    .clearAuthentication(true) // ???????????? ??????
//                .and()
//                .addFilterBefore()
//                    .oauth2Login() // oauth2 ????????? ????????? ?????????
//                    .successHandler(jwtSuccessHandler) // ????????? ?????? ??? ?????????
//                    .failureHandler(null) // ????????? ?????? ??? ?????????
//                .userInfoEndpoint() // ????????? ?????? ?????? ????????? ?????? ????????? ?????? ??????
//                .userService(customOAuth2UserService) // ????????? ?????? ??? ??????????????? ????????? UserService??? ????????? ??????
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

        List<ClientRegistration> registrations = new ArrayList<>();
        registrations.add(this.googleClientRegistration());
        registrations.add(this.kakaoClientRegistration());

        return new InMemoryClientRegistrationRepository(registrations);
    }

    private ClientRegistration googleClientRegistration() {
        log.info("====googleClientRegistration====");
        return ClientRegistration.withRegistrationId("google")
                .clientId(env.getProperty(CLIENT_PROPERTY_KEY + ".google.client-id"))
                .clientSecret(env.getProperty(CLIENT_PROPERTY_KEY + ".google.client-secret"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri(env.getProperty(CLIENT_PROPERTY_KEY+".google.redirect-uri"))
//                .scope(env.getProperty(CLIENT_PROPERTY_KEY + ".google.scope"))
                .scope("profile", "email")
                .authorizationUri("https://accounts.google.com/o/oauth2/v2/auth")
                .tokenUri("https://www.googleapis.com/oauth2/v4/token")
                .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
                .userNameAttributeName(IdTokenClaimNames.SUB)
                .jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
                .clientName("Google")
                .build();
    }

    private ClientRegistration kakaoClientRegistration() {
        log.info("====kakaoClientRegistration====");
        return ClientRegistration.withRegistrationId("kakao")
                .clientId(env.getProperty(CLIENT_PROPERTY_KEY + ".kakao.client-id"))
                .clientSecret(env.getProperty(CLIENT_PROPERTY_KEY + ".kakao.client-secret"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri(env.getProperty(CLIENT_PROPERTY_KEY+".kakao.redirect-uri"))
                .scope("profile", "account_email")
                .authorizationUri("https://kauth.kakao.com/oauth/authorize")
                .tokenUri("https://kauth.kakao.com/oauth/token")
                .userInfoUri("https://kapi.kakao.com/v2/user/me")
                .userNameAttributeName(IdTokenClaimNames.SUB)
//                .jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
                .clientName("kakao")
                .build();
    }

}
