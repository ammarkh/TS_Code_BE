package com.marketplace.CodeChallenge.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

/**
 * @author ammar
 * @since 2022-03
 */

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table
public class MarketplaceService {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "seq_service_id")
    @SequenceGenerator(name = "seq_service_id",
            sequenceName = "seq_service_id",
            allocationSize = 1, initialValue = 1)
    private long serviceId;
    @NotNull
    private String name;
    @NotNull
    private double price;
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime startDate;
    @Column(columnDefinition = "int default 1")
    private long workingTimeInDay;
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expiredDate;
    @Column(columnDefinition = "int default 0")
    private boolean product;
    private String description;

    @JoinColumn(name = "type_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private ServiceType type;

    @JoinColumn(name = "provider_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ServiceProvider serviceProvider;

    @OneToMany(mappedBy = "service")
    @JsonIgnore
    private Set<MarketplaceClientService> clientServices;
}
