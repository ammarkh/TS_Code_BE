package com.marketplace.CodeChallenge.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "MarketplaceClientService")
public class MarketplaceClientService {
    /**
     * the best datatype for id of this entity should be UUID,
     * I used Long for basic use
     */
    @Id
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "client_id")
    private Client client;


    @ManyToOne
//    @MapsId("serviceId")
    @JoinColumn(name = "service_id")
    private MarketplaceService service;

    @Column(columnDefinition = "int default 0")
    private boolean completed;

    @Column(columnDefinition = "int default 0")
    private boolean canceled;

    private LocalDateTime expired;

    public boolean hasLiveService() {
        return !this.isCompleted()
                && !this.isCanceled()
                && this.expired.isAfter(LocalDateTime.now());
    }
}
