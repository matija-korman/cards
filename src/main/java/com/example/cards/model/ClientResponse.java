package com.example.cards.model;

import com.example.cards.domain.Status;
import lombok.Data;

@Data
public class ClientResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String oib;

    private Status status;
}
