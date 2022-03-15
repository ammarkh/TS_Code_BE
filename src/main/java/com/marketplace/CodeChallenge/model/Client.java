package com.marketplace.CodeChallenge.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table
public class Client {
    @Id
    @SequenceGenerator(name = "seq_client_id", initialValue = 1,
            allocationSize = 1, sequenceName = "seq_client_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "seq_client_id")
    private long clientId;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @Column(columnDefinition = "int default 0")
    private boolean disabled;

    @OneToMany(mappedBy = "client")
    @JsonIgnore
    private Set<MarketplaceClientService> service;

    public Client(Long clientId, String username, String password) {
        this.username = username;
        this.password = password;
        this.clientId = clientId;
    }
}
