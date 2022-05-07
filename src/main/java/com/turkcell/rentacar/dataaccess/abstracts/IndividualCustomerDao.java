package com.turkcell.rentacar.dataaccess.abstracts;

import org.springframework.stereotype.Repository;

import com.turkcell.rentacar.entities.concretes.IndividualCustomer;

@Repository
public interface IndividualCustomerDao extends UserDao<IndividualCustomer> {

    boolean existsIndividualCustomerByNationalIdentity(String nationalIdentity);
}
