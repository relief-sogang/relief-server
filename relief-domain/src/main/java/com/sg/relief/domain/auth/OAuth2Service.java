package com.sg.relief.domain.auth;

import com.sg.relief.domain.auth.user.GoogleUserInfo;
import com.sg.relief.domain.auth.user.KakaoUserInfo;
import com.sg.relief.domain.auth.user.OAuth2UserInfo;
import com.sg.relief.domain.code.Role;
import com.sg.relief.domain.code.UserStatus;
import com.sg.relief.domain.persistence.entity.User;
import com.sg.relief.domain.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class OAuth2Service {

    private final ClientRegistrationRepository clientRegistrationRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;


    @Transactional
    public LoginResponse login(String providerName, String code){
        ClientRegistration provider = clientRegistrationRepository.findByRegistrationId(providerName);
        OAuth2TokenResponse tokenResponse = getToken(code, provider);

        User user = getUserProfile(providerName, tokenResponse.getAccessToken(), provider);

        String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(user.getId()));
        String refreshToken = jwtTokenProvider.createRefreshToken();

        return LoginResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

    }

    private OAuth2TokenResponse getToken(String code, ClientRegistration provider){

        return WebClient.create()
                .post()
                .uri(provider.getProviderDetails().getTokenUri())
                .headers(header-> {
                    header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                })
                .bodyValue(tokenRequest(code, provider))
                .retrieve()
                .bodyToMono(OAuth2TokenResponse.class)
                .block();


    }

    private MultiValueMap<String, String> tokenRequest(String code, ClientRegistration provider){
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code");
        formData.add("client_id", provider.getClientId());
        formData.add("redirect_uri", provider.getRedirectUri());
        formData.add("code", code);
        formData.add("client_secret", provider.getClientSecret());
        return formData;
    }

    private User getUserProfile(String providerName, String oAuth2AccessToken, ClientRegistration provider){
        Map<String, Object> userAttributes = getUserAttributes(provider, oAuth2AccessToken);
        OAuth2UserInfo oauth2UserInfo = null;
        log.info("providerName: {}", providerName);
        if(providerName.equals("kakao")){
            oauth2UserInfo = new KakaoUserInfo(userAttributes);
        } else if (providerName.equals("google")){
            oauth2UserInfo = new GoogleUserInfo(userAttributes);
        } else {
            log.info("허용되지 않은 접근입니다.");
        }

        String name = oauth2UserInfo.getName();
        String email = oauth2UserInfo.getEmail();

        Optional<User> user = userRepository.findByEmail(email);

        if(user.isEmpty()){
            return userRepository.save(User.builder()
                    .name(name)
                    .email(email)
                    .role(Role.USER)
                    .status(UserStatus.CREATED)
                    .createdAt(new Date())
                    .modifiedAt(new Date())
                    .build())
                    ;
        } else {
            return user.get();
        }
    }

    private Map<String, Object> getUserAttributes(ClientRegistration provider, String oAuth2AccessToken){
        return WebClient.create()
                .get()
                .uri(provider.getProviderDetails().getUserInfoEndpoint().getUri())
                .headers(header->header.setBearerAuth(oAuth2AccessToken))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
    }

}
