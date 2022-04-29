package com.turkcell.rentacar.entities.concretes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "corporate_customers")
@Builder
public class CorporateCustomer extends Customer {

	@Column(name = "company_name")
	private String companyName;

	@Column(name = "tax_number", length = 11)
	private String taxNumber;


}
