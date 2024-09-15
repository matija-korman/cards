package com.example.cards.controller;

import com.example.cards.controller.mapper.ClientMapper;
import com.example.cards.domain.Client;
import com.example.cards.model.ClientCardRequest;
import com.example.cards.model.ClientCreateRequest;
import com.example.cards.model.ClientResponse;
import com.example.cards.service.CardService;
import com.example.cards.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final CardService  cardService;
    private final ClientMapper clientMapper;

    @PostMapping("/clients")
    public ResponseEntity<ClientResponse> createClient(@RequestBody @Valid ClientCreateRequest request) {
        Client client = clientMapper.clientCreateRequestToClient(request);
        client = clientService.createClient(client);
        ClientResponse response = clientMapper.clientToClientResponse(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/clients/{oib}")
    public ResponseEntity<ClientResponse> getClientByOib(@PathVariable String oib) {
        Client client = clientService.getClientByOib(oib);
        ClientResponse response = clientMapper.clientToClientResponse(client);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/clients/{oib}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteClient(@PathVariable String oib) {
        this.clientService.deleteByOib(oib);
    }

    @PostMapping("/clients/cards")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void requestNewCard(@RequestBody @Valid ClientCardRequest request) {
        cardService.createCard(request);
    }
}
