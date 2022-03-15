package com.marketplace.CodeChallenge.controller;

import com.marketplace.CodeChallenge.dto.ClientRequestDto;
import com.marketplace.CodeChallenge.dto.ClientResponseDto;
import com.marketplace.CodeChallenge.model.Client;
import com.marketplace.CodeChallenge.service.ClientService;
import com.marketplace.CodeChallenge.service.ClientServiceService;
import com.marketplace.CodeChallenge.util.MapperUtil;
import com.marketplace.CodeChallenge.util.response.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @author ammar
 * @since 2022
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private ClientServiceService marketplaceClientService;

    @PostMapping("/add")
    public ResponseEntity addNewClient(@RequestBody @Validated ClientRequestDto client) {
        try {
            if (this.clientService
                    .isClientExist(client.getUsername()))
                return new ResponseEntity<>(new
                        CustomResponse(HttpStatus.CONFLICT,
                        "This Username Is Already Taken"),
                        HttpStatus.CONFLICT);
            Client newClient =
                    this.clientService
                            .saveClient(
                                    MapperUtil.map(client, Client.class)
                            );

            return ResponseEntity.ok(
                    MapperUtil.map(newClient, ClientResponseDto.class)
            );
        } catch (Exception ex) {
            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }

    @PostMapping("/signin")
    public ResponseEntity signIn(@RequestBody ClientRequestDto signClient) {
        Client isClient =
                this.clientService
                        .findClient(signClient.getUsername(),
                                signClient.getPassword());

        return Objects.nonNull(isClient)
                ? ResponseEntity
                .ok(MapperUtil.map(isClient,
                        ClientResponseDto.class))
                : new ResponseEntity<>(new
                CustomResponse(HttpStatus.NOT_FOUND,
                "username or password is not correct"),
                HttpStatus.NOT_FOUND);

    }

    @PutMapping("/edit")
    public ResponseEntity updateClient(@RequestBody @Validated ClientRequestDto client) {
        try {

            Client updateClient =
                    this.clientService
                            .updateClient(MapperUtil.map(client, Client.class),
                                    client.getClientId());

            return ResponseEntity.ok(
                    MapperUtil.map(updateClient,
                            ClientResponseDto.class)
            );
        } catch (Exception ex) {
            return new ResponseEntity<>(
                    new CustomResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                            ex.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

    }

    /**
     * this function is required for admin dashboard , it will not be used in current version
     *
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity getClients() {
        try {
            List<Client> clients = this
                    .clientService.fetchClient();

            if (clients.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return ResponseEntity
                    .ok(
                            MapperUtil.mapAll(clients,
                                    ClientResponseDto.class)
                    );
        } catch (Exception ex) {
            return new ResponseEntity<>(
                    new CustomResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                            ex.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

    }

    @GetMapping("/{clientId}")
    public ResponseEntity getClient(@PathVariable Long clientId) {
        Client client = this.clientService.fetchClientById(clientId);

        return Objects.nonNull(client)
                ? ResponseEntity.ok(
                MapperUtil.map(client, ClientResponseDto.class)
        ) :
                new ResponseEntity<>(
                        new CustomResponse(HttpStatus.NOT_FOUND,
                                "new Client with id " + clientId),
                        HttpStatus.NOT_FOUND
                );

    }

    @GetMapping("/service/{clientId}")
    public ResponseEntity getClientService(
            @PathVariable("clientId") Long clientId) {
        return ResponseEntity.ok(
                this.marketplaceClientService
                        .getServiceByClientId(clientId)
        );
    }

    @PutMapping("/service/canceled/{clientServiceId}")
    public ResponseEntity cancelService(@PathVariable("clientServiceId")
                                                Long clientServiceId) {
        this.marketplaceClientService
                .canceledService(clientServiceId);
        return new ResponseEntity<>(
                new CustomResponse(
                        HttpStatus.OK,
                        "service has been canceled"
                ),
                HttpStatus.OK
        );
    }

    @PutMapping("/service/extend/{clientServiceId}")
    public ResponseEntity extendService(@PathVariable("clientServiceId")
                                                Long clientServiceId) {
        this.marketplaceClientService
                .extendService(clientServiceId);

        return new ResponseEntity<>(
                new CustomResponse(
                        HttpStatus.OK,
                        "service has been extend"
                ),
                HttpStatus.OK
        );
    }


}
