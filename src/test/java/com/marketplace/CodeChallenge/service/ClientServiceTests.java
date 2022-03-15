package com.marketplace.CodeChallenge.service;

import com.marketplace.CodeChallenge.model.Client;
import com.marketplace.CodeChallenge.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ClientServiceTests {
    @MockBean
    private ClientRepository clientRepository;

    @BeforeEach
    void initClientService() {
        assertThat(clientRepository).isNotNull();
    }

    @Test
    void saveClient() throws Exception {
        Client client = new Client();
        client.setUsername("client");
        client.setPassword("12345");

         Mockito.when(clientRepository.save(client)).thenReturn(client);

    }


}
