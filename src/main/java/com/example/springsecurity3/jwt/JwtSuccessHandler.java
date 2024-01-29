package com.example.springsecurity3.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.springsecurity3.config.auth.PrincipalDetails;
import com.example.springsecurity3.dto.GeneratedToken;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtSuccessHandler implements AuthenticationSuccessHandler {

    private  final JwtUtil jwtUtil;
    private final JwtProperties jwtProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("successfullAuthentication 실행됨.");
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        GeneratedToken token =
                jwtUtil.generateToken(
                        principalDetails.getUser().getEmail(),
                        principalDetails.getUser().getRole());


        response.getWriter().write("WOW");
        response.addHeader(jwtProperties.getHaederAccess(),jwtProperties.getTokenPrefix()+token.getAccessToken());
        response.addHeader(jwtProperties.getHaederRefresh(),jwtProperties.getTokenPrefix()+token.getRefreshToken());

    }
}
