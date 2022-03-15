package com.marketplace.CodeChallenge.repository;

import com.marketplace.CodeChallenge.model.ServiceType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderTypeRepository
        extends CrudRepository<ServiceType, Long> {
}
