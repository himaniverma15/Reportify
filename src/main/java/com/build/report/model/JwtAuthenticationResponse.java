package com.build.report.model;

public class JwtAuthenticationResponse {
    private String accessToken;

    public JwtAuthenticationResponse(String jwt) {
        this.accessToken = jwt;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
