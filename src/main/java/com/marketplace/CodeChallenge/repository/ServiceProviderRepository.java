package com.marketplace.CodeChallenge.repository;

import com.marketplace.CodeChallenge.model.ServiceProvider;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceProviderRepository
        extends CrudRepository<ServiceProvider, Long> {

    ServiceProvider searchServiceProviderByUsername
            (String username);

    ServiceProvider searchServiceProviderByUsernameAndPassword
            (String username, String password);
}
