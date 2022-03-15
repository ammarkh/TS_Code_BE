package com.marketplace.CodeChallenge.repository;

import com.marketplace.CodeChallenge.model.MarketplaceClientService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MarketplaceClientServiceRepository extends
        JpaRepository<MarketplaceClientService, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO MARKETPLACE_CLIENT_SERVICE " +
            "(ID, SERVICE_ID, CLIENT_ID, EXPIRED, COMPLETED, Canceled )" +
            "Values(:id,:serviceId, :clientId, :expired, :completed, :canceled)",
            nativeQuery = true)
    void saveService(
            @Param("id") Long id,
            @Param("serviceId") Long serviceId,
            @Param("clientId") Long clientId,
            @Param("expired") LocalDateTime expired,
            @Param("completed") boolean completed,
            @Param("canceled") boolean canceled);

    @Query(value = "SELECT max(M.id) from MarketplaceClientService M ")
    Long getMaxId();

    @Transactional
    @Modifying
    @Query("UPDATE MarketplaceClientService M " +
            "SET M.canceled = TRUE WHERE M.id = :clientServiceId")
    void canceledService(Long clientServiceId);

    @Modifying
    @Transactional
    @Query("UPDATE MarketplaceClientService M " +
            "SET M.expired = :extendFor , M.canceled = false , M.completed = False" +
            " WHERE M.id = :clientServiceId ")
    void extendService(Long clientServiceId, LocalDateTime extendFor);

    @Transactional
    @Modifying
    @Query("UPDATE MarketplaceClientService M " +
            "SET M.completed = TRUE WHERE M.id = :clientServiceId")
    void completedService(Long clientServiceId);

    List<MarketplaceClientService> getMarketplaceClientServiceByClient_ClientId(Long clientId);


}
