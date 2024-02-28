package com.nhnacademy.inkbridge.backend.dto;

import lombok.Data;

/**
 * class: TokenResponse.
 *
 * @author jeongbyeonghun
 * @version 2/28/24
 */

@Data
public class TokenResponse {

    private Access access;

    @Data

    private static class Access {

        private Token token;


        @Data
        private static class Token {

            public String id;
            public String expires;
            public String issued_at;

        }

    }

    public String getTokenId() {
        return this.access.token.id;
    }

}