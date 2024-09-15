package com.example.cards.controller.mapper;

import com.example.cards.domain.Client;
import com.example.cards.model.ClientCardRequest;
import com.example.cards.model.ClientCreateRequest;
import com.example.cards.model.ClientResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    Client clientCreateRequestToClient(ClientCreateRequest clientCreateRequest);

    ClientResponse clientToClientResponse(Client client);

    ClientCardRequest clientToClientCardRequest(Client client);
}
