package com.turkcell.rentacar.entities.concretes;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "invoices")
public class Invoice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "invoice_id")
	private int invoiceId;

	@Column(name = "invoice_number")
	private String invoiceNumber;

	@Column(name = "creation_date")
	private LocalDate creationDate;

	@Column(name = "rental_start_date")
	private LocalDate rentalStartDate;

	@Column(name = "rental_return_date")
	private LocalDate rentalReturnDate;

	@Column(name = "total_rental_day")
	private int totalRentDay;

	@Column(name = "total_price")
	private Double totalPrice;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "rental_id")
	private Rental rental;

	@OneToOne(mappedBy = "invoice")
	private Payment payment;

}
