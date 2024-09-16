package com.example.cards.service;

import com.example.cards.domain.Client;
import com.example.cards.domain.Status;
import com.example.cards.model.ClientStatus;
import com.example.cards.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@KafkaListener(topics = "client-status-topic", groupId = "client-status-group", autoStartup = "false", containerFactory = "kafkaListenerContainerFactory")
public class ClientStatusKafkaListener {

    private final ClientRepository clientRepository;

    @KafkaHandler
    public void consume(ClientStatus clientStatus) {
        Optional<Client> client = clientRepository.findById(clientStatus.getClientId());
        client.ifPresent(c -> {
            c.setStatus(Status.valueOf(String.valueOf(clientStatus.getStatus())));
            clientRepository.save(c);
        });
    }
}
