package com.sg.relief.interfaces.api.query;

import com.sg.relief.domain.auth.LoginResponse;
import com.sg.relief.domain.auth.OAuth2Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class OAuthController {

    @Autowired
    private OAuth2Service oAuth2Service;

    @GetMapping("/oauth2/code/{provider}")
    public ResponseEntity<LoginResponse> login(@PathVariable String provider, @RequestParam String code){
        LoginResponse loginResponse = oAuth2Service.login(provider, code);
        return ResponseEntity.ok().body(loginResponse);
    }
}
