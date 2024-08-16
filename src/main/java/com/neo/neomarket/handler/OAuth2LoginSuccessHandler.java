package com.neo.neomarket.handler;

import com.neo.neomarket.repository.mysql.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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

        // 세션에 OAuth2User 객체를 저장
        HttpSession session = request.getSession();
        session.setAttribute("oauthUser", oAuth2User);

        String email = oAuth2User.getAttribute("email");
        String redirectUrl;

        try {
            if (email == null) {
                throw new IllegalArgumentException("Email not provided by OAuth provider");
            }

            if (userRepository.findByEmail(email).isPresent()) {
                redirectUrl = frontendUrl + "/";
            } else {
                redirectUrl = frontendUrl + "/register";
            }
        } catch (Exception e) {
            // 에러 발생 시 프론트엔드의 에러 페이지로 리다이렉트
            redirectUrl = "http://localhost:3000/login?error=" + e.getMessage();
        }

        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}