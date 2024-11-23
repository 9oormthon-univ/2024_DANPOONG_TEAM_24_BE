package com.ieum.be.domain.auth.filter;


import com.ieum.be.domain.auth.utility.JwtUtility;
import com.ieum.be.global.response.BaseResponse;
import com.ieum.be.global.response.GeneralResponse;
import com.ieum.be.global.response.GlobalException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtility jwtUtility;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JwtFilter(JwtUtility jwtUtility) {
        this.jwtUtility = jwtUtility;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @Nonnull HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 인증 없이 접근 가능한 경로인지 확인
        String requestURI = request.getRequestURI();

        if (requestURI.startsWith("/recipes/generate") || requestURI.startsWith("/recipes/options")) {
            // 인증 없이 처리할 경로는 바로 필터 체인으로 넘김
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = resolveToken(request, response);

        if (accessToken == null) {
            return;
        }

        try {
            Jws<Claims> claims = jwtUtility.getClaimsFromToken(accessToken);

            String email = claims.getPayload().get("email", String.class);

            System.out.println(email);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);

        } catch (GlobalException e) {
            resolveException(e.getStatus(), response);
        }
    }

    private String resolveToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String accessToken = request.getHeader("Authorization");

        if (accessToken == null) {
            resolveException(GeneralResponse.NO_JWT_TOKEN, response);
            return null;
        }

        if (!accessToken.startsWith("Bearer ")) {
            resolveException(GeneralResponse.INVALID_JWT_TOKEN, response);
            return null;
        }

        return accessToken.substring(7);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().equals("/oauth");
    }

    private void resolveException(GeneralResponse generalResponse, HttpServletResponse response) throws IOException {
        response.setStatus(generalResponse.getCode());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String jsonResponse = objectMapper.writeValueAsString(BaseResponse.of(generalResponse));
        response.getWriter().write(jsonResponse);
    }
}
