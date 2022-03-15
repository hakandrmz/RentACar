package com.turkcell.rentacar.business.abstracts;


import com.turkcell.rentacar.business.dtos.customerDtos.CorporateCustomerByIdDto;
import com.turkcell.rentacar.business.dtos.customerDtos.CorporateCustomerListDto;
import com.turkcell.rentacar.business.requests.customer.corporateCustomer.CreateCorporateCustomerRequest;
import com.turkcell.rentacar.business.requests.customer.corporateCustomer.UpdateCorporateCustomerRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

import java.util.List;

public interface CorporateCustomerService {

    DataResult<List<CorporateCustomerListDto>> getAll();

    Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest);

    Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest);

    Result delete(int deleteCorporateCustomerId);

    DataResult<CorporateCustomerByIdDto> getCorporateCustomerByUserId(int userId);
}

