package com.turkcell.rentacar.entities.concretes;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "customer")
public class Customer extends User{
    @Column(name = "customer_id")
    private int customerId;

    @OneToMany(mappedBy="customer")
    private List<Invoice> invoices;

}
