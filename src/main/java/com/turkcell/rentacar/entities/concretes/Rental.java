package com.turkcell.rentacar.entities.concretes;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "rentals")
@Builder
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_id")
    private int rentalId;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ordered_additional_service_id")
    private OrderedAdditionalService orderedAdditionalServices;

    @OneToOne
    @JoinColumn(name = "rented_city",referencedColumnName = "city_id")
    private City rentedCity;

    @OneToOne
    @JoinColumn(name = "delivered_city",referencedColumnName = "city_id")
    private City deliveredCity;

    @OneToMany(mappedBy="rental")
    private List<Invoice> invoices;
}
