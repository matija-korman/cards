package com.example.cards.service;

import com.example.cards.controller.exception.ExternalAPIException;
import com.example.cards.model.ClientCardRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;


@Service
@Slf4j
@RequiredArgsConstructor
public class CardService {

    private final RestTemplate restTemplate;
    private static final String EXTERNAL_API_URL = "https://api.something.com/v1/api/v1/card-request";

    public void createCard(ClientCardRequest request) {
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(EXTERNAL_API_URL, request, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("Successfully created card for client (oib: {})", request.getOib());
            } else {
                log.error("Call to card create api failed for client (oib: {}) with code {} and error: {}",
                        request.getOib(), response.getStatusCode(), response.getBody());
                throw new ExternalAPIException("Received unexpected status code from external API: " + response.getStatusCode());
            }
        } catch (ResourceAccessException e) {
            throw new ExternalAPIException("Failed to connect to external API: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new ExternalAPIException("An unexpected error occurred while communicating with the external API", e);
        }
    }
}
