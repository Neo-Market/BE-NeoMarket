package com.neo.neomarket.controller;


import com.neo.neomarket.dto.AuthResultDTO;
import jakarta.servlet.http.HttpSession;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequiredArgsConstructor
public class OAuthController {
    Logger logger = LoggerFactory.getLogger(OAuthController.class);
    private static final String LOGIN_SUCCESS_MESSAGE = "로그인 성공!";
    private static final String LOGIN_FAILURE_MESSAGE = "로그인 실패. 다시 시도해주세요.";

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;

    @GetMapping("/api/login")
    public ResponseEntity<Map<String, String>> getGoogleAuthUrl() {
        String authUrl = UriComponentsBuilder.fromHttpUrl("https://accounts.google.com/o/oauth2/v2/auth")
                .queryParam("client_id", googleClientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("response_type", "code")
                .queryParam("scope", "email profile")
                .build().toUriString();

        logger.info("Generated Google OAuth2 URL: " + authUrl);

        return ResponseEntity.ok(Map.of("authorizationUrl", authUrl));
    }

    @GetMapping("/login/oauth2/code/google")
    public ResponseEntity<AuthResultDTO> googleCallback(@AuthenticationPrincipal OAuth2User principal) {
        if (principal != null) {
            return ResponseEntity.ok(new AuthResultDTO(LOGIN_SUCCESS_MESSAGE));
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new AuthResultDTO(LOGIN_FAILURE_MESSAGE));
        }
    }

}