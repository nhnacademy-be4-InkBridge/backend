package com.nhnacademy.inkbridge.backend.dto;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * class: TokenResponse.
 *
 * @author jeongbyeonghun
 * @version 2/28/24
 */

@Setter
@NoArgsConstructor
public class TokenResponse {

    private Access access;


    @Setter
    @NoArgsConstructor
    public static class Access {

        private Token token;

        @Builder
        public Access(Token token) {
            this.token = token;
        }

        @Setter
        @NoArgsConstructor
        public static class Token {

            private String id;
            private String expires;
            private String issuedAt;

            @Builder
            public Token(String id, String expires, String issuedAt) {
                this.id = id;
                this.expires = expires;
                this.issuedAt = issuedAt;
            }
        }

    }

    @Builder
    public TokenResponse(Access access) {
        this.access = access;
    }

    public String getTokenId() {
        return this.access.token.id;
    }

}