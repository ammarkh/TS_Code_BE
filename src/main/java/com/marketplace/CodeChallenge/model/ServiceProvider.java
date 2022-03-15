package com.marketplace.CodeChallenge.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table
public class ServiceProvider {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "seq_provider_id")
    @SequenceGenerator(name = "seq_provider_id",
            sequenceName = "seq_provider_id",
            allocationSize = 1, initialValue = 1)
    private Long providerId;
    @NotNull
    private String username;
    @NotNull
    private String name;
    @NotNull
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "serviceProvider", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<MarketplaceService> servicesOffered;
}
