package com.marketplace.CodeChallenge.service;

import com.marketplace.CodeChallenge.model.MarketplaceService;
import com.marketplace.CodeChallenge.repository.MarketplaceServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class MarketplaceServiceService {
    @Autowired
    private MarketplaceServiceRepository marketplaceServiceRepository;

    public MarketplaceService saveService(MarketplaceService marketplaceService) {
        return this.marketplaceServiceRepository
                .save(marketplaceService);
    }

    public MarketplaceService updateService
            (MarketplaceService service, Long serviceId) {

        MarketplaceService oldService = this.marketplaceServiceRepository
                .findById(serviceId).orElse(null);

        if (oldService == null)
            return null;

        if (Objects.nonNull(service.getName())
                && !"".equalsIgnoreCase(service.getName()))
            oldService.setName(service.getName());

        if (Objects.nonNull(service.getPrice()))
            oldService.setPrice(service.getPrice());

        if (Objects.nonNull(service.getExpiredDate()))
            oldService.setExpiredDate(service.getExpiredDate());

        if (Objects.nonNull(service.getWorkingTimeInDay()))
            oldService.setWorkingTimeInDay(service.getWorkingTimeInDay());

        return this.marketplaceServiceRepository
                .save(oldService);
    }

    public MarketplaceService fetchService(Long serviceId) {
        return this.marketplaceServiceRepository
                .findById(serviceId).orElse(null);
    }

    public List<MarketplaceService> fetchServices() {
        return (List<MarketplaceService>) this
                .marketplaceServiceRepository.findAll();
    }

    public void deleteService(Long serviceId) {
        this.marketplaceServiceRepository
                .deleteById(serviceId);
    }

    public List<MarketplaceService> getProviderServices(Long providerId) {
        return this.marketplaceServiceRepository
                .findMarketplaceServiceByServiceProvider_ProviderId(
                        providerId);
    }

    public List<MarketplaceService> search(String keyword) {
        return  this
                .marketplaceServiceRepository
                .searchAllByNameAndDescription(keyword);
    }



}
