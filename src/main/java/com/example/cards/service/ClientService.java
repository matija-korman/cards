package com.example.cards.service;

import com.example.cards.domain.Client;
import com.example.cards.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public Client createClient(Client product) {
        return clientRepository.save(product);
    }

    public Client getClientByOib(String oib) {
        return clientRepository.findByOib(oib);
    }

    public void deleteByOib(String oib) {
        clientRepository.deleteByOib(oib);
    }
}
