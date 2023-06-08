package com.sahay.auth;


public record AuthRequest(
        String username,
        String password
) {
}
