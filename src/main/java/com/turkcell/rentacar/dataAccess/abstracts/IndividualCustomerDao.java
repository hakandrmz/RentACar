package com.turkcell.rentacar.dataAccess.abstracts;

import com.turkcell.rentacar.entities.concretes.IndividualCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IndividualCustomerDao extends JpaRepository<IndividualCustomer,Integer> {
    boolean existsByNationalIdentity(String identityNumber);
    boolean existsIndividualCustomerByEmail(String mail);
}
