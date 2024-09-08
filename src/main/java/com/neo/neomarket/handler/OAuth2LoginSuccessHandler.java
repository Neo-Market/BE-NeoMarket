package com.neo.neomarket.handler;

import com.neo.neomarket.repository.mysql.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@PropertySource("classpath:application-oauth.properties")
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${app.frontend.url}")
    private String frontendUrl;

    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String email = oAuth2User.getAttribute("email");

        String redirectUrl = determineRedirectUrl(email);

        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }

    // 리다이렉트 URL 결정 로직 분리
    private String determineRedirectUrl(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email not provided by OAuth provider");
        }

        // 사용자 회원가입 여부 확인
        return userRepository.findByEmail(email).isPresent()
                ? frontendUrl + "/"         // 회원가입 된 사용자는 홈으로
                : frontendUrl + "/register"; // 회원가입 되지 않은 사용자는 회원가입 페이지로
    }
}