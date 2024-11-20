package com.ieum.be.domain.oauth.controller;

import com.ieum.be.domain.oauth.service.OAuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth")
public class OAuthController {
    private final OAuthService oAuthService;

    public OAuthController(OAuthService oAuthService) {
        this.oAuthService = oAuthService;
    }

    @GetMapping
    public ResponseEntity<?> authenticate(HttpServletRequest request) {
        return this.oAuthService.authenticate(request);
    }
}
