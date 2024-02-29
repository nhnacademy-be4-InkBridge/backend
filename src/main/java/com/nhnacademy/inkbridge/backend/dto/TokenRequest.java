package com.nhnacademy.inkbridge.backend.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

/**
 * class: TokenRequest.
 *
 * @author jeongbyeonghun
 * @version 2/28/24
 */
@Getter
public class TokenRequest {

    private final Auth auth;

    @Getter
    public static class Auth {

        private final String tenantId;
        private final PasswordCredentials passwordCredentials;

        @Builder
        public Auth(String tenantId, PasswordCredentials passwordCredentials) {
            this.tenantId = tenantId;
            this.passwordCredentials = passwordCredentials;
        }
    }

    @Getter
    public static class PasswordCredentials {

        private final String username;
        private final String password;

        @Builder
        public PasswordCredentials(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }

    @Builder
    public TokenRequest(String tenantId, String username, String password) {
        PasswordCredentials passwordCredentials = PasswordCredentials.builder().username(username)
            .password(password).build();
        this.auth = Auth.builder().tenantId(tenantId).passwordCredentials(passwordCredentials)
            .build();
    }


}