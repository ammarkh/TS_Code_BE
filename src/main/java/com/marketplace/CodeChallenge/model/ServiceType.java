package com.marketplace.CodeChallenge.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
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
public class ServiceType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "seq_service_type_id")
    @SequenceGenerator(name = "seq_service_type_id",
            sequenceName = "seq_service_type_id",
            allocationSize = 1, initialValue = 1)
    private long typeId;
    @NotNull
    private String name;

    @CreationTimestamp
    private LocalDateTime creationDate;

    @OneToMany(mappedBy = "type")
    private Set<MarketplaceService> services;
}
