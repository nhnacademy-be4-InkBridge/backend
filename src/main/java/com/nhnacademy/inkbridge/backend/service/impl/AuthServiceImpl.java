package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.dto.TokenRequest;
import com.nhnacademy.inkbridge.backend.dto.TokenResponse;
import com.nhnacademy.inkbridge.backend.service.AuthService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * class: AuthService.
 *
 * @author jeongbyeonghun
 * @version 2/28/24
 */

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private static final String AUTH_URL = "https://api-identity-infrastructure.nhncloudservice.com/v2.0";
    private static final String TENANT_ID = "e805e9a72d2f47338a0a463196c36314";
    private static final String USERNAME = "wjdqudgns23@naver.com";
    private static final String PASSWORD = "bhC$050319";

    @Override
    public String requestToken() {
        TokenRequest tokenRequest = TokenRequest.builder().tenantId(TENANT_ID).username(USERNAME)
            .password(PASSWORD).build();

        RestTemplate restTemplate = new RestTemplate();
        String identityUrl = AUTH_URL + "/tokens";

        // 헤더 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        HttpEntity<TokenRequest> httpEntity
            = new HttpEntity<>(tokenRequest, headers);

        // 토큰 요청
        ResponseEntity<TokenResponse> response
            = restTemplate.exchange(identityUrl, HttpMethod.POST, httpEntity, TokenResponse.class);

        return Objects.requireNonNull(response.getBody()).getTokenId();
    }
}
