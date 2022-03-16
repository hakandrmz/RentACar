package com.turkcell.rentacar.entities.concretes;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "customer_id", referencedColumnName = "user_id")
@Data
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "customer")
public class Customer extends User {

    @Column(name = "customer_id", insertable = false, updatable = false)
    private int customerId;

    @OneToMany(mappedBy = "customer")
    private List<Rental> rentals;

}
