package com.marketplace.CodeChallenge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketplace.CodeChallenge.model.Client;
import com.marketplace.CodeChallenge.service.ClientService;
import com.marketplace.CodeChallenge.service.ClientServiceService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientController.class)
public class ClientControllerTests {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    @MockBean
    private ClientService clientService;
    @MockBean
    private ClientServiceService marketplaceClientService;


    Client client = new Client(1L, "client", "1234");
    Client client1 = new Client(2L, "client1", "1234");
    Client client2 = new Client(3L, "client2", "1234");

    @Test
    void getAllClients_success() throws Exception {
        List<Client> clients = new ArrayList<>(Arrays.asList(client, client1, client2));

        Mockito.when(clientService.fetchClient()).thenReturn(clients);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/client/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.body", Matchers.hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.body[2].username", is("client2")));

    }

    @Test
    void getClientById_success() throws Exception {
        Mockito.when(clientService.fetchClientById(client1.getClientId())).thenReturn((client1));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/client/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.body.username", is("client1")));
    }

    @Test
    void createClient_success() throws Exception {
        Client client = Client.builder()
                .username("John_Doe")
                .password("1234")
                .clientId(1)
                .build();

        Mockito.when(clientService.saveClient(client)).thenReturn(client);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/client/add")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(client));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.body", notNullValue()))
                .andExpect(jsonPath("$.response.body.username", is("John_Doe")));
    }

    @Test
    void createClient_conflict() throws Exception {
        Client client = Client.builder()
                .username("client")
                .password("1234")
                .build();

        Mockito.when(clientService.isClientExist(client.getUsername())).thenReturn(true);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/client/add")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(client));

        mockMvc.perform(mockRequest)
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.response.body", notNullValue()))
                .andExpect(jsonPath("$.response.body.message", is("This Username Is Already Taken")));
    }


    @Test
    void singInClient_fail() throws Exception {

        Client client = Client.builder()
                .username("client")
                .password("1234")
                .build();

        Mockito.when(clientService.findClient(client.getUsername(), client.getPassword())).thenReturn(null);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/client/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(client));


        mockMvc.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.response.body", notNullValue()))
                .andExpect(jsonPath("$.response.body.message", is("username or password is not correct")));
    }

    @Test
    void updatePatientRecord_notFoundElement() throws Exception {
        Client updatedRecord = Client.builder()
                .clientId(1)
                .password("password")
                .build();


        Mockito.when(clientService.updateClient(updatedRecord, client.getClientId())).thenReturn(null);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/client/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(updatedRecord));

        mockMvc.perform(mockRequest)
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.response.body", notNullValue()))
                .andExpect(jsonPath("$.response.body.message", is("source cannot be null")));
    }

    @Test
    void updatePatientRecord_noId() throws Exception {
        Client updatedRecord = Client.builder()
                .password("password")
                .build();


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/client/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(updatedRecord));

        mockMvc.perform(mockRequest)
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.response.body", notNullValue()))
                .andExpect(jsonPath("$.response.body.message", is("source cannot be null")));
    }


}
