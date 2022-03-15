package com.marketplace.CodeChallenge.service;

import com.marketplace.CodeChallenge.model.ServiceType;
import com.marketplace.CodeChallenge.repository.ProviderTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author ammar
 * since 2022-03
 */
@Service
public class ProviderTypeService {
    @Autowired
    private ProviderTypeRepository providerTypeRepository;

    public ServiceType saveProviderType(ServiceType serviceType) {
        return this.providerTypeRepository
                .save(serviceType);
    }

    public ServiceType updateProviderType(ServiceType serviceType, Long typeId) {
        ServiceType type = this.providerTypeRepository
                .findById(typeId).orElse(null);

        if (type == null)
            return null;

        if (Objects.nonNull(serviceType.getName())
                && !"".equalsIgnoreCase(serviceType.getName()))
            type.setName(serviceType.getName());

        return this.providerTypeRepository.save(type);
    }

    public List<ServiceType> fetchTypes() {
        return (List<ServiceType>) this
                .providerTypeRepository.findAll();
    }

    public ServiceType fetchType(Long typeId) {
        return this.providerTypeRepository
                .findById(typeId).orElse(null);
    }

    public void deleteType(long typeId) {
        this.providerTypeRepository.deleteById(typeId);
    }
}
