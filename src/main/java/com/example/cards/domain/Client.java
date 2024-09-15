package com.example.cards.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Clients")
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "oib")
    private String oib;

    @Column(name = "statusid")
    private Status status;
}
