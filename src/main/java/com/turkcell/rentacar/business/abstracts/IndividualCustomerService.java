package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.business.dtos.customerDtos.IndividualCustomerByIdDto;
import com.turkcell.rentacar.business.dtos.customerDtos.IndividualCustomerListDto;
import com.turkcell.rentacar.business.requests.customer.individualCustomer.CreateIndividualCustomerRequest;
import com.turkcell.rentacar.business.requests.customer.individualCustomer.UpdateIndividualCustomerRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

import java.util.List;

public interface IndividualCustomerService {

    DataResult<List<IndividualCustomerListDto>> getAll();

    Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest);

    Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest);

    Result delete(int individualCustomerId);

    DataResult<IndividualCustomerByIdDto> getIndividualCustomerByIdDtoByUserId(int userId);
}

