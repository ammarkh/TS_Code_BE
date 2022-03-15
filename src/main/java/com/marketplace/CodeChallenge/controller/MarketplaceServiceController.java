package com.marketplace.CodeChallenge.controller;

import com.marketplace.CodeChallenge.model.MarketplaceClientService;
import com.marketplace.CodeChallenge.model.MarketplaceService;
import com.marketplace.CodeChallenge.service.ClientServiceService;
import com.marketplace.CodeChallenge.service.MarketplaceServiceService;
import com.marketplace.CodeChallenge.util.response.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


@RestController
@CrossOrigin("*")
@RequestMapping("/service")
public class MarketplaceServiceController {
    @Autowired
    private MarketplaceServiceService marketplaceServiceService;
    @Autowired
    private ClientServiceService clientServiceService;
    @Value("${serviceAllowed.number}")
    private String numberOfAllowedLiveTaskForProvider;

    @PostMapping("/add")
    public ResponseEntity addService(@RequestBody MarketplaceService service) {
       // System.out.println(numberOfAllowedLiveTaskForProvider);
        return ResponseEntity.ok(this
                .marketplaceServiceService.saveService(service));
    }


    /**
     * this function for admin dashboard
     *
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity getServices() {
        return ResponseEntity.ok(this.marketplaceServiceService
                .fetchServices());
    }

    @PostMapping("/buy")
    public ResponseEntity buyService(@RequestBody MarketplaceClientService marketplaceClientService) {
        try {
            this.clientServiceService.buyService(marketplaceClientService);
            return new ResponseEntity<>(new
                    CustomResponse(HttpStatus.CREATED,
                    "service has been buying successfully"),
                    HttpStatus.CREATED);

        } catch (Exception ex) {
            return new ResponseEntity<>(
                    new CustomResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                            ex.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }


    }

    @GetMapping("/{serviceId}")
    public ResponseEntity getServiceById(@PathVariable("serviceId")
                                                 long serviceId) {
        MarketplaceService service =
                this.marketplaceServiceService.fetchService(serviceId);
        return Objects.nonNull(service)
                ? ResponseEntity.ok(service)
                : new ResponseEntity<>(
                        new CustomResponse(
                                HttpStatus.NOT_FOUND,
                                "no service with id"+ serviceId
                        ),HttpStatus.NOT_FOUND
                  );


    }

    @GetMapping("/catalog")
    public ResponseEntity getAvailableServices() {
        //get all live task
        List<MarketplaceClientService> clientServices =
                this.clientServiceService
                        .getMarketplaceClientService()
                        .stream()
                        .filter(MarketplaceClientService::hasLiveService)
                        .collect(Collectors.toList());

        //get map <providerId, count of providers> that represent live task
        Map<Long, Long> providers = clientServices.stream()
                .collect(Collectors.
                        groupingBy(cs -> cs.getService().getServiceProvider().getProviderId(),
                                Collectors.counting()));
        // filter Provider Map by allowed task number;
        List<Long> providersKey = new ArrayList<>(); //contains all providers has exceeded Allowed live task
        for (Map.Entry<Long, Long> entry : providers.entrySet()) {
            if (entry.getValue() >= (Integer.parseInt(numberOfAllowedLiveTaskForProvider)))
                providersKey.add(entry.getKey());
        }

        List<MarketplaceService> services =
                this.marketplaceServiceService
                        .fetchServices();

        List<MarketplaceService> availableServices = services.stream()
                .filter(s -> !providersKey.contains(s.getServiceProvider().getProviderId()))
                .collect(Collectors.toList());


        return ResponseEntity.ok(availableServices);
    }

    @GetMapping("/search")
    public ResponseEntity search(@RequestParam("q") String keyword) {
        List<MarketplaceService> services =
                this.marketplaceServiceService
                        .search(keyword);

        if (services.isEmpty())
            return new ResponseEntity<>(
                    new CustomResponse(HttpStatus.NOT_FOUND,
                            "no service found like your search "),
                    HttpStatus.NOT_FOUND
            );

        return ResponseEntity.ok(services);
    }

    @PutMapping("/complete/{clientServiceId}")
    public ResponseEntity completeService(@PathVariable("clientServiceId")
                                        Long clientServiceId) {
        this.clientServiceService
                .completedService(clientServiceId);

        return new ResponseEntity<>(
                new CustomResponse(
                        HttpStatus.OK,
                        "service has been completed"
                ),
                HttpStatus.OK
        );
    }

}
