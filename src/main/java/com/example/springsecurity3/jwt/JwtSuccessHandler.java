package com.example.springsecurity3.jwt;

import com.example.springsecurity3.config.auth.PrincipalDetails;
import com.example.springsecurity3.dto.GeneratedToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("successfullAuthentication 실행됨.");
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        GeneratedToken token =
                jwtUtil.generateToken(
                        principalDetails.getUser().getUsername(),
                        objectMapper.writeValueAsString(principalDetails.getUser().getAuthorityList())
                );

        // body를 통해 access token과 refresh token 전달
        String result = objectMapper.writeValueAsString(token);
        response.getWriter().write(result);

        // header를 통해 access token과 refresh token 전달
//        response.addHeader(jwtProperties.getHeaderAccess(),jwtProperties.getTokenPrefix()+token.getAccessToken());
//        response.addHeader(jwtProperties.getHeaderRefresh(),jwtProperties.getTokenPrefix()+token.getRefreshToken());
    }
}
