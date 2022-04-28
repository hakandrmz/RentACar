package com.turkcell.rentacar.entities.concretes;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "brands")
@Builder
public class Brand {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "brand_id")
	private int id;

	@Column(name = "brand_name")
	private String brandName;

	@OneToMany(mappedBy = "brand")
	private List<Car> cars;
}
