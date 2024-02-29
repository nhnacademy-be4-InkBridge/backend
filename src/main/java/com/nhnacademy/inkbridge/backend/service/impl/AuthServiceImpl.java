package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.dto.TokenRequest;
import com.nhnacademy.inkbridge.backend.dto.TokenResponse;
import com.nhnacademy.inkbridge.backend.service.AuthService;
import java.util.Objects;
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
public class AuthServiceImpl implements AuthService {
    String authUrl = "https://api-identity-infrastructure.nhncloudservice.com/v2.0";
    String tenantId = "e805e9a72d2f47338a0a463196c36314";
    String username = "wjdqudgns23@naver.com";
    String password = "bhC$050319";

    TokenRequest tokenRequest;

    @Override
    public String requestToken() {
        this.tokenRequest = TokenRequest.builder().tenantId(tenantId).username(username).password(password).build();

        RestTemplate restTemplate = new RestTemplate();
        String identityUrl = this.authUrl + "/tokens";

        // 헤더 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        HttpEntity<TokenRequest> httpEntity
            = new HttpEntity<>(this.tokenRequest, headers);

        // 토큰 요청
        ResponseEntity<TokenResponse> response
            = restTemplate.exchange(identityUrl, HttpMethod.POST, httpEntity, TokenResponse.class);

        return Objects.requireNonNull(response.getBody()).getTokenId();
    }
}
