package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.config.KeyConfig;
import com.nhnacademy.inkbridge.backend.dto.TokenRequest;
import com.nhnacademy.inkbridge.backend.dto.TokenResponse;
import com.nhnacademy.inkbridge.backend.property.ObjectStorageProperty;
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

    private final KeyConfig keyConfig;
    private final ObjectStorageProperty objectStorageProperty;

    @Override
    public String requestToken() {
        TokenRequest tokenRequest = TokenRequest.builder().tenantId(keyConfig.keyStore(objectStorageProperty.getTenantId())).username(keyConfig.keyStore(objectStorageProperty.getStorageUrl()))
            .password(keyConfig.keyStore(objectStorageProperty.getPassword())).build();

        RestTemplate restTemplate = new RestTemplate();
        String identityUrl = keyConfig.keyStore(objectStorageProperty.getAuthUrl()) + "/tokens";

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
