package com.neo.neomarket.controller;


import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
    public void redirectToGoogleAuth(HttpServletResponse response) throws IOException {
        String authUrl = UriComponentsBuilder.fromHttpUrl("https://accounts.google.com/o/oauth2/v2/auth")
                .queryParam("client_id", googleClientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("response_type", "code")
                .queryParam("scope", "openid profile email")
                .build().toUriString();

        logger.info("Generated Google OAuth2 URL: " + authUrl);

        // 클라이언트를 구글 인증 페이지로 리디렉션
        response.sendRedirect(authUrl);
    }

}