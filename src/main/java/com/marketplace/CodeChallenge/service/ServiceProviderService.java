package com.marketplace.CodeChallenge.service;

import com.marketplace.CodeChallenge.model.ServiceProvider;
import com.marketplace.CodeChallenge.repository.ServiceProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ServiceProviderService {
    @Autowired
    private ServiceProviderRepository serviceProviderRepository;

    public ServiceProvider registerProvider(ServiceProvider provider) {
        return this.serviceProviderRepository.save(provider);
    }

    public ServiceProvider updateProvider(ServiceProvider provider, Long providerId) {
        ServiceProvider oldProvider = this
                .serviceProviderRepository.findById(providerId)
                .orElse(null);

        if (oldProvider == null)
            return null;

        if (Objects.nonNull(provider.getPassword())
                && !"".equalsIgnoreCase(provider.getPassword()))
            oldProvider.setPassword(provider.getPassword());

        return serviceProviderRepository.save(oldProvider);
    }

    public ServiceProvider fetchProvider(Long providerId) {
        return this
                .serviceProviderRepository.findById(providerId)
                .orElse(null);
    }

    /**
     * this function for fetch list of providers , this function available for admin dashboard
     *
     * @return
     */
    public List<ServiceProvider> fetchProviders() {
        return (List<ServiceProvider>) this
                .serviceProviderRepository.findAll();
    }

    /**
     * this funciton for delete specific provider by id , this function available only for admin dashboard
     *
     * @param providerId
     */
    public void deleteProvider(long providerId) {
        this.serviceProviderRepository.deleteById(providerId);
    }

    public boolean isProviderExist(String username) {
        return this.serviceProviderRepository
                .searchServiceProviderByUsername(username) != null;
    }

    public ServiceProvider findProvider(String username, String password) {
        return this.serviceProviderRepository
                .searchServiceProviderByUsernameAndPassword(username, password);
    }
}
