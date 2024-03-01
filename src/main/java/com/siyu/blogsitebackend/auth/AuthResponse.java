package com.siyu.blogsitebackend.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {
    private String token;

    public AuthResponse() {
    }

    public AuthResponse(String token) {
        this.token = token;
    }
}
