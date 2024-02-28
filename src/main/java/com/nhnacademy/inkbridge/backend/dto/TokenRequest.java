package com.nhnacademy.inkbridge.backend.dto;

import lombok.Data;

/**
 * class: TokenRequest.
 *
 * @author jeongbyeonghun
 * @version 2/28/24
 */
@Data
public class TokenRequest {

    private Auth auth = new Auth();

    @Data
    public static class Auth {

        private String tenantId;
        private PasswordCredentials passwordCredentials = new PasswordCredentials();

    }

    @Data
    public static class PasswordCredentials {

        private String username;
        private String password;
    }
}
