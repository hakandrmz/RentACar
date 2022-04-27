package com.turkcell.rentacar.entities.concretes;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "rentals")
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_id")
    private int rentalId;

    @Column(name = "start_date")
    private LocalDate rentStartDate;

    @Column(name = "return_date")
    private LocalDate rentalReturnDate;

    @Column(name = "start_kilometer")
    private Double startKilometer;

    @Column(name = "end_kilometer")
    private Double endKilometer;

    @ManyToOne
    @JoinColumn(name = "rental_city_id")
    private City rentalCity;

    @ManyToOne
    @JoinColumn(name = "return_city_id")
    private City returnCity;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @OneToMany(mappedBy = "rental")
    private List<OrderedService> orderedServices;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Customer customer;

    @OneToMany(mappedBy = "rental")
    private List<Invoice> invoice;

    @OneToMany(mappedBy = "rental")
    private List<Payment> payments;
}
