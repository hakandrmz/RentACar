package com.turkcell.rentacar.dataAccess.abstracts;
import org.springframework.data.jpa.repository.JpaRepository;
import com.turkcell.rentacar.entities.concretes.AdditionalService;

public interface AdditionalServiceDao extends JpaRepository<AdditionalService, Integer> {

}
