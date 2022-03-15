package com.marketplace.CodeChallenge.service;

import com.marketplace.CodeChallenge.model.MarketplaceClientService;
import com.marketplace.CodeChallenge.model.MarketplaceService;
import com.marketplace.CodeChallenge.repository.ClientRepository;
import com.marketplace.CodeChallenge.repository.MarketplaceClientServiceRepository;
import com.marketplace.CodeChallenge.repository.MarketplaceServiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ClientServiceService {

    private MarketplaceClientServiceRepository serviceRepository;
    private MarketplaceServiceRepository marketplaceServiceRepository;

    public void buyService(MarketplaceClientService clientService) {
        MarketplaceService service = this
                .marketplaceServiceRepository
                .findById(clientService.getService().getServiceId())
                .orElse(null);

        this.serviceRepository.saveService(
                serviceRepository.getMaxId() == null ? 1 : serviceRepository.getMaxId() + 1,
                clientService.getService().getServiceId(),
                clientService.getClient().getClientId(),
                LocalDateTime.now().plusDays(service.getWorkingTimeInDay()),
                false,
                false);
    }

    private Long getMaxId() {
        return this.serviceRepository.getMaxId();
    }

    public List<MarketplaceClientService> getServiceByClientId(Long clientId) {
        return (List<MarketplaceClientService>)
                this.serviceRepository
                        .getMarketplaceClientServiceByClient_ClientId(clientId);
    }

    public void canceledService(Long clientServiceId) {
        this.serviceRepository
                .canceledService(clientServiceId);
    }

    public void extendService(Long clientServiceId) {
        MarketplaceClientService clientService =
                this.serviceRepository
                        .getById(clientServiceId);

        Long extendPeriod =
                clientService.getService()
                        .getWorkingTimeInDay();

        this.serviceRepository.extendService(clientServiceId,
                clientService.getExpired().plusDays(extendPeriod));

    }

    public void completedService(Long clientServiceId) {
        this.serviceRepository
                .completedService(clientServiceId);
    }

    public List<MarketplaceClientService> getMarketplaceClientService() {
        return this.serviceRepository
                .findAll();
    }


}
