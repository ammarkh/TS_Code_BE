package com.marketplace.CodeChallenge.repository;

import com.marketplace.CodeChallenge.model.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {

    Client searchClientByUsername(String username);

    Client searchClientByUsernameAndPassword(String username,
                                             String password);
}
