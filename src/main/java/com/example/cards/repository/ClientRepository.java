package com.example.cards.repository;

import com.example.cards.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Client findByOib(String oib);

    void deleteByOib(String oib);

}
