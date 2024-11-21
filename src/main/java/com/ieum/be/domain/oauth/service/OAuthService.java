package com.ieum.be.domain.oauth.service;

import com.ieum.be.domain.auth.utility.JwtUtility;
import com.ieum.be.domain.oauth.component.OAuthComponent;
import com.ieum.be.domain.oauth.constant.KakaoUrl;
import com.ieum.be.domain.oauth.constant.RequestParameter;
import com.ieum.be.domain.oauth.dto.KakaoTokenResponse;
import com.ieum.be.domain.oauth.dto.KakaoUserDataDto;
import com.ieum.be.domain.oauth.dto.KakaoUserInformationDto;
import com.ieum.be.domain.user.entity.User;
import com.ieum.be.domain.user.repository.UserRepository;
import com.ieum.be.global.constant.TokenType;
import com.ieum.be.global.response.BaseResponse;
import com.ieum.be.global.response.GeneralResponse;
import com.ieum.be.global.response.GlobalException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class OAuthService {
    private final UserRepository userRepository;
    private final OAuthComponent oAuthComponent;
    private final JwtUtility jwtUtility;

    public OAuthService(OAuthComponent oAuthComponent, UserRepository userRepository, JwtUtility jwtUtility) {
        this.userRepository = userRepository;
        this.oAuthComponent = oAuthComponent;
        this.jwtUtility = jwtUtility;
    }

    public ResponseEntity<?> authenticate(HttpServletRequest request) {
        String code = request.getParameter(RequestParameter.CODE.getValue());

        if (code == null) {
            throw new GlobalException(GeneralResponse.INTERNAL_SERVER_ERROR);
        }

        return requestAccessToken(request, code);
    }

    public KakaoUserDataDto getDataUsingToken(String accessToken) {
        RestClient restClient = RestClient.builder().baseUrl(oAuthComponent.API_URI).build();

        KakaoUserInformationDto kakaoUserInformationDto = restClient.get()
                .uri(KakaoUrl.GET_USER_INFORMATION)
                .header(HttpHeaders.AUTHORIZATION, TokenType.BEARER + accessToken)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (request, response) -> {
                    throw new GlobalException(GeneralResponse.REJECTED);
                })
                .body(KakaoUserInformationDto.class);

        if (kakaoUserInformationDto == null || kakaoUserInformationDto.getKakaoAccount() == null) {
            throw new GlobalException(GeneralResponse.INTERNAL_SERVER_ERROR);
        }

        if (kakaoUserInformationDto.getKakaoAccount().getEmail() == null || kakaoUserInformationDto.getKakaoAccount().getEmail().isEmpty()) {
            throw new GlobalException(GeneralResponse.INTERNAL_SERVER_ERROR);
        }

        return KakaoUserDataDto.builder()
                .email(kakaoUserInformationDto.getKakaoAccount().getEmail())
                .nickname(kakaoUserInformationDto.getKakaoAccount().getProfile().getNickname())
                .profileImageUrl(kakaoUserInformationDto.getKakaoAccount().getProfile().getProfileImageUrl())
                .build();
    }

    private ResponseEntity<?> requestAccessToken(HttpServletRequest req, String code) {
        RestClient restClient = RestClient.builder().baseUrl(oAuthComponent.AUTH_URI).build();

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();

        requestBody.add(RequestParameter.CODE.getValue(), code);
        requestBody.add(RequestParameter.GRANT_TYPE.getValue(), oAuthComponent.GRANT_TYPE);
        requestBody.add(RequestParameter.CLIENT_ID.getValue(), oAuthComponent.CLIENT_ID);
        requestBody.add(RequestParameter.REDIRECT_URI.getValue(), oAuthComponent.REDIRECT_URI + "/auth");

        KakaoTokenResponse tokenResponse = restClient.post()
                .uri(KakaoUrl.GET_TOKEN)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .acceptCharset(StandardCharsets.UTF_8)
                .body(requestBody)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (request, response) -> {
                    throw new GlobalException(GeneralResponse.REJECTED);
                })
                .body(KakaoTokenResponse.class);

        if (tokenResponse == null) {
            throw new GlobalException(GeneralResponse.INTERNAL_SERVER_ERROR);
        }

        return readUserInformation(tokenResponse.getAccessToken());
    }

    public ResponseEntity<?> readUserInformation(String kakaoAccessToken) {
        KakaoUserDataDto kakaoUserDataDto = getDataUsingToken(kakaoAccessToken);

        String email = kakaoUserDataDto.getEmail();
        String nickname = kakaoUserDataDto.getNickname();
        String profileImageUrl = kakaoUserDataDto.getProfileImageUrl();

        User user = this.userRepository.findUserByEmail(email).orElse(null);

        if (user != null) {
            Map<String, String> claims = new HashMap<>();
            claims.put("email", email);

            return jwtUtility.generateJwtResponse(claims);
        }

        User newUser = User.builder()
                .email(email)
                .name(nickname)
                .profileUrl(profileImageUrl)
                .build();

        this.userRepository.save(newUser);

        Map<String, String> claims = new HashMap<>();
        claims.put("email", kakaoUserDataDto.getEmail());

        return jwtUtility.generateJwtResponse(claims);
    }
}