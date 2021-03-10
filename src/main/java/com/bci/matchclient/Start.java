package com.bci.matchclient;

import com.bci.matchclient.authenticate.HttpAuthenticate;
import com.bci.matchclient.facade.BciFacade;
import com.bci.matchclient.authenticate.OAuth2Response;
import com.bci.matchclient.model.Root;
import com.bci.matchclient.requestmodel.Queries;
import com.bci.matchclient.requestmodel.Query;
import com.bci.matchclient.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class Start implements CommandLineRunner {

    private final Logger log = LoggerFactory.getLogger(Start.class);
    private OAuth2Response oAuth2Response;

    @Value("${microservice.oauth2.token.endpoint}")
    private String authenticationUrl;
    @Value("${microservice.oauth2.token.clientid}")
    private String clientId;
    @Value("${microservice.oauth2.token.clientsecret}")
    private String clientSecret;
    @Value("${authentication.grant-type}")
    private String grantType;
    @Value("${authentication.scope}")
    private String scope;
    @Value("${freg.base-url}")
    private String baseUrl;



    @PostConstruct
    public void init() {
        oAuth2Response = new OAuth2Response();
    }

    @Override
    public void run(String... args) throws Exception {

        //Get access token, or refresh existing token if it expires in less than 20 minutes
        if (getAccessTokenOrRefresh()) {
            refreshToken();
        } else {
            displayStatus("Using existing access token");
        }

        List<Query> queries = new ArrayList<>();
        addToQueryList(queries);
        Queries queries1 = new Queries(queries);

        BciFacade bciFacade = new BciFacade(baseUrl);
        ResponseEntity<Root> response = bciFacade.getPersonOppslagBulk(queries1, oAuth2Response.getAccess_token());

        //print
        System.out.println(response.getBody().matchResponses.get(0).matchCandidates.get(0).person.familyName);

    }

    private void addToQueryList(List<Query> queries) {
        Query query = new Query();
        query.setSourceCountry("NO");
        query.setDateOfBirth("");
        query.setFirstName("Ola");
        query.setFamilyName("Nordmann");
        query.setStreetAddress("Langkaia 1");
        query.setCity("Oslo");
        queries.add(query);
    }

    private void refreshToken() {
        displayStatus("Getting new access token..");
        HttpAuthenticate httpManager = new HttpAuthenticate(authenticationUrl);
        try {
            ResponseEntity<String> response = httpManager.authenticate(clientId, clientSecret, grantType, scope);
            if (response.getStatusCode() == HttpStatus.OK) {
                oAuth2Response = new OAuth2Response(response.getBody());
                displayStatus("Access token OK!");

                //Track when token expires
                oAuth2Response.setExpirationMs(System.currentTimeMillis() + (oAuth2Response.getExpires_in() * 1000));
            } else {
                log.error("Error getting access token, HTTP status: {} - {}", response.getStatusCode(),response);
            }
        } catch (Exception e) {
            log.error("Error getting access token: {}", e.getMessage());
        }
    }

    private boolean getAccessTokenOrRefresh() {
        return Util.isNullOrEmpty(oAuth2Response.getAccess_token()) ||
                Util.calculateSecondsBetween(System.currentTimeMillis(), oAuth2Response.getExpirationMs()) < 1200;
    }

    private void displayStatus(String log) {
        this.log.info("{}", log);
    }


}
