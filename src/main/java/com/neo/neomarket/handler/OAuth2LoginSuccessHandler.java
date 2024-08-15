package com.neo.neomarket.handler;

import com.neo.neomarket.repository.mysql.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // 세션에 OAuth2User 객체를 저장
        HttpSession session = request.getSession();
        session.setAttribute("oauthUser", oAuth2User);

        String email = oAuth2User.getAttribute("email");
        if (userRepository.findByEmail(email).isPresent()) {
            getRedirectStrategy().sendRedirect(request, response, "http://localhost:3000/");
        } else {
            getRedirectStrategy().sendRedirect(request, response, "http://localhost:3000/register");
        }
    }
}
