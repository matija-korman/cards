package com.example.cards.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClientCreateRequest {

    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String oib;
    @NotNull
    private Status status;
}
