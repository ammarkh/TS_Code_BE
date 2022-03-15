package com.marketplace.CodeChallenge.service;

import com.marketplace.CodeChallenge.model.Client;
import com.marketplace.CodeChallenge.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Transactional
    public Client saveClient(Client newClient) {
        return this.clientRepository.save(newClient);
    }

    @Transactional
    public Client updateClient(Client client, long clientId) {
        Client oldClient = this.clientRepository.
                findById(clientId).orElse(null);

        if (oldClient == null)
            return null;

        if (Objects.nonNull(client.getPassword())
                && !"".equalsIgnoreCase(client.getPassword()))
            oldClient.setPassword(client.getPassword());

        if (Objects.nonNull(client.isDisabled()))
            oldClient.setDisabled(client.isDisabled());

        return this.clientRepository.save(oldClient);
    }

    public List<Client> fetchClient() {
        return (List<Client>) this.clientRepository.findAll();
    }

    public Client fetchClientById(long clientId) {
        return this.clientRepository
                .findById(clientId).orElse(null);
    }

    public boolean isClientExist(String username) {
        return this.clientRepository
                .searchClientByUsername(username) != null;
    }

    public Client findClient(String username, String password) {
        return this.clientRepository
                .searchClientByUsernameAndPassword(
                        username, password
                );
    }

    public void deleteClient(long clientId) {
        this.clientRepository
                .deleteById(clientId);
    }
}
