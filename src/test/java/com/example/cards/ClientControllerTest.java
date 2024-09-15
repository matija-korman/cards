package com.example.cards;

import com.example.cards.controller.exception.ExternalAPIException;
import com.example.cards.domain.Client;
import com.example.cards.model.ClientCardRequest;
import com.example.cards.model.ClientCreateRequest;
import com.example.cards.model.ClientResponse;
import com.example.cards.model.Status;
import com.example.cards.repository.ClientRepository;
import com.example.cards.service.CardService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ClientControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ClientRepository clientRepository;
    @MockBean
    private CardService cardService;

    @Test
    void testCreateClient() {
        ClientCreateRequest request = crateClientCreateRequest();

        ResponseEntity<ClientResponse> response = this.restTemplate.postForEntity("http://localhost:" + port + "/clients", request, ClientResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getFirstName()).isEqualTo(request.getFirstName());
        assertThat(response.getBody().getLastName()).isEqualTo(request.getLastName());
        assertThat(response.getBody().getOib()).isEqualTo(request.getOib());
        assertThat(response.getBody().getStatus().name()).isEqualTo(request.getStatus().name());
    }

    @Test
    void testGetClientByOib() {
        Client client = createClient();
        clientRepository.save(client);

        ResponseEntity<ClientResponse> response = this.restTemplate.getForEntity("http://localhost:" + port + "/clients/{oib}", ClientResponse.class, client.getOib());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getFirstName()).isEqualTo(client.getFirstName());
        assertThat(response.getBody().getLastName()).isEqualTo(client.getLastName());
        assertThat(response.getBody().getOib()).isEqualTo(client.getOib());
        assertThat(response.getBody().getStatus().name()).isEqualTo(client.getStatus().name());
    }

    @Test
    void testDeleteClientByOib() {
        Client client = createClient();
        clientRepository.save(client);

        Client clientFromDb = clientRepository.findByOib(client.getOib());
        assertThat(clientFromDb).isNotNull();

        this.restTemplate.exchange("http://localhost:" + port + "/clients/{oib}", HttpMethod.DELETE, null, Void.class, client.getOib());

        clientFromDb = clientRepository.findByOib(client.getOib());
        assertThat(clientFromDb).isNull();
    }

    @Test
    void testCreateClientCard() {
        ClientCardRequest request = crateClientCardRequest();

        Mockito.doNothing().when(cardService).createCard(request);

        ResponseEntity<Void> response = this.restTemplate.postForEntity("http://localhost:" + port + "/clients/cards", request, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void testCreateClientCard_throwsException() {
        ClientCardRequest request = crateClientCardRequest();

        Mockito.doThrow(ExternalAPIException.class).when(cardService).createCard(request);

        ResponseEntity<Void> response = this.restTemplate.postForEntity("http://localhost:" + port + "/clients/cards", request, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_GATEWAY);
    }

    private static ClientCreateRequest crateClientCreateRequest() {
        ClientCreateRequest request = new ClientCreateRequest();
        request.setFirstName("Testing");
        request.setLastName("Test");
        request.setOib("12345678910");
        request.setStatus(Status.PENDING);
        return request;
    }

    private static ClientCardRequest crateClientCardRequest() {
        ClientCardRequest request = new ClientCardRequest();
        request.setFirstName("Testing");
        request.setLastName("Test");
        request.setOib("12345678910");
        request.setStatus(Status.PENDING);
        return request;
    }

    private static Client createClient() {
        Client client = new Client();
        client.setFirstName("Testing");
        client.setLastName("Test");
        client.setOib("12345678910");
        client.setStatus(com.example.cards.domain.Status.PENDING);
        return client;
    }
}
