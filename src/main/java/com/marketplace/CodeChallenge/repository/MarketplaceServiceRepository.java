package com.marketplace.CodeChallenge.repository;

import com.marketplace.CodeChallenge.model.MarketplaceService;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarketplaceServiceRepository extends
        CrudRepository<MarketplaceService, Long> {

    List<MarketplaceService>
    findMarketplaceServiceByServiceProvider_ProviderId(
            Long providerId);

    @Query("SELECT s FROM MarketplaceService s WHERE CONCAT (s.name, s.description) Like %?1% ")
    List<MarketplaceService> searchAllByNameAndDescription(@Param("keyword") String keyword);

}
