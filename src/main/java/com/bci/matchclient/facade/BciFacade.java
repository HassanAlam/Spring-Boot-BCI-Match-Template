package com.bci.matchclient.facade;

import com.bci.matchclient.model.Root;
import com.bci.matchclient.requestmodel.Queries;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
public class BciFacade {

    private WebClient webClient;

    public BciFacade(String url) {
        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(5_000_000)).build();
        this.webClient = WebClient.builder()
                .exchangeStrategies(exchangeStrategies)
                .baseUrl(url)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public ResponseEntity<Root> getPersonOppslagBulk(Queries queries, String token) throws JsonProcessingException {
        return webClient.post()
                .body(BodyInserters.fromValue(queries))
                .header("Authorization","Bearer "+ token)
                .retrieve()
                .toEntity(Root.class)
                .block();
    }
}
