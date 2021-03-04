package com.bci.matchclient.http;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class HttpAuthenticate {

    private String url;

    public HttpAuthenticate(String url) {
        this.url = url;
    }

    /**
     * Access token authenticate
     * @param clientId
     * @param clientSecret
     * @param grantType
     * @return
     */
    public ResponseEntity<String> authenticate(String clientId, String clientSecret, String grantType, String scope) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);
        map.add("grant_type", grantType);
        map.add("scope", scope);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class);
        return response;
    }
}
