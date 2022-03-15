package com.marketplace.CodeChallenge.controller;

import com.marketplace.CodeChallenge.dto.ClientResponseDto;
import com.marketplace.CodeChallenge.dto.ProviderRequestDto;
import com.marketplace.CodeChallenge.dto.ProviderResponseDto;
import com.marketplace.CodeChallenge.model.MarketplaceService;
import com.marketplace.CodeChallenge.model.ServiceProvider;
import com.marketplace.CodeChallenge.service.MarketplaceServiceService;
import com.marketplace.CodeChallenge.service.ServiceProviderService;
import com.marketplace.CodeChallenge.util.MapperUtil;
import com.marketplace.CodeChallenge.util.response.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Objects;


@RestController
@CrossOrigin("*")
@RequestMapping("/provider")
public class ProviderController {
    @Autowired
    private ServiceProviderService providerService;
    @Autowired
    private MarketplaceServiceService marketplaceServiceService;


    @PostMapping("/register")
    public ResponseEntity registerProvider(@Validated @RequestBody ProviderRequestDto provider) {
        try {

            if (this.providerService
                    .isProviderExist(provider.getUsername()))
                return new ResponseEntity<>(new
                        CustomResponse(HttpStatus.CONFLICT,
                        "This Username Is Already Taken"),
                        HttpStatus.CONFLICT);

            ServiceProvider serviceProvider =
                    this.providerService.registerProvider(
                            MapperUtil.map(provider, ServiceProvider.class)
                    );

            return ResponseEntity.created(
                            URI.create("/api/v1/marketplace/provider/" + provider.getProviderId())
                    )
                    .body(MapperUtil.map(serviceProvider, ProviderResponseDto.class));
        } catch (Exception ex) {
            ex.getStackTrace();
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/signin")
    public ResponseEntity signIn(@RequestBody ProviderRequestDto provider) {
        ServiceProvider isProvider =
                this.providerService
                        .findProvider(provider.getUsername(), provider.getPassword());

        return Objects.nonNull(isProvider)
                ? ResponseEntity
                .ok(MapperUtil.map(isProvider,
                        ProviderResponseDto.class))
                : new ResponseEntity<>(new
                CustomResponse(HttpStatus.NOT_FOUND,
                "username or password is not correct"),
                HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{providerId}")
    public ResponseEntity getProvider(@PathVariable long providerId) {

        ServiceProvider provider =
                this.providerService.fetchProvider(providerId);


        return ResponseEntity.ok(
                MapperUtil.map(provider, ProviderResponseDto.class)
        );
    }

    @GetMapping("/services/{providerId}")
    public ResponseEntity getProviderServices(
            @PathVariable("providerId") Long providerId) {
        return ResponseEntity.ok(
                this.marketplaceServiceService
                        .getProviderServices(providerId)
        );
    }

    @PostMapping("/service/add")
    public ResponseEntity addNewService(@RequestBody MarketplaceService service) {
        return ResponseEntity.ok(
                this.marketplaceServiceService
                        .saveService(service)
        );
    }

}
