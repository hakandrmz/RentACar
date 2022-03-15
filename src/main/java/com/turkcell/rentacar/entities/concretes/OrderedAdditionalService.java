package com.turkcell.rentacar.entities.concretes;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.turkcell.rentacar.business.abstracts.AdditionalServiceService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ordered_additional_services")
@Builder
public class OrderedAdditionalService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ordered_additional_service_id")
    private int id;

    @Column(name = "ordered_additional_service_uuid")
    private UUID orderedAdditionalServiceId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="additional_service_id")
    private AdditionalService additionalService;

    @OneToOne(mappedBy = "orderedAdditionalServices")
    private Rental rental;
}
