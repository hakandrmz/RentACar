package com.turkcell.rentacar.entities.concretes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "customer")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "Lazy"})
@PrimaryKeyJoinColumn(name = "customer_id", referencedColumnName = "user_id")
public class Customer extends User {

    @Column(name = "customer_id", insertable = false, updatable = false)
    private int customerId;

    @OneToMany(mappedBy = "customer")
    private List<Rental> rentals;

    @OneToMany(mappedBy = "customer")
    private List<CreditCard> userCardInformations;
}
