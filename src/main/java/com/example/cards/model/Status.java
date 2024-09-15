package com.example.cards.model;

import lombok.Getter;

@Getter
public enum Status {
    PENDING(1),
    APPROVED(2),
    REJECTED(3),
    COMPLETED(4);

    private final Integer id;

    Status(int id){
        this.id = id;
    }

}
