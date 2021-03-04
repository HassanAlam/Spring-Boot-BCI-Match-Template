package com.bci.matchclient.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Properties received from OAuth token provider
 */
public class OAuth2Response {
    private String access_token;
    private String scope;
    private String token_type;
    private long expires_in;
    private long expirationMs;


    public OAuth2Response() {
    }

    public OAuth2Response(String jsonBody) throws JsonProcessingException {
        OAuth2Response oAuth2Response = parseOAuth2Response(jsonBody);
        this.access_token = oAuth2Response.getAccess_token();
        this.scope = oAuth2Response.getScope();
        this.token_type = oAuth2Response.getToken_type();
        this.expires_in = oAuth2Response.getExpires_in();
    }

    public OAuth2Response(String access_token, String scope, String token_type, long expires_in) {
        this.access_token = access_token;
        this.scope = scope;
        this.token_type = token_type;
        this.expires_in = expires_in;
    }

    /**
     * Get access token from body
     * @param jsonBody
     * @return
     * @throws Exception
     */
    private OAuth2Response parseOAuth2Response(String jsonBody) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        OAuth2Response oAuth2Response = objectMapper.readValue(jsonBody, OAuth2Response.class);
        return oAuth2Response;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public long getExpirationMs() {
        return expirationMs;
    }

    public void setExpirationMs(long expirationMs) {
        this.expirationMs = expirationMs;
    }
}