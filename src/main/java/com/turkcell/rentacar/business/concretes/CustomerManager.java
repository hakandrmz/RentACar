package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.CustomerService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.dtos.customer.CustomerListDto;
import com.turkcell.rentacar.business.dtos.customer.GetCustomerDto;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.exceptions.customer.CustomerNotFoundException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.dataAccess.abstracts.CustomerDao;
import com.turkcell.rentacar.entities.concretes.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerManager implements CustomerService {

    private final CustomerDao customerDao;
    private final ModelMapperService modelMapperService;

    @Autowired
    public CustomerManager(CustomerDao customerDao, ModelMapperService modelMapperService) {

        this.customerDao = customerDao;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public DataResult<List<CustomerListDto>> getAll() {

        List<Customer> result = this.customerDao.findAll();

        List<CustomerListDto> response = result.stream().map(customer -> this.modelMapperService
                .forDto().map(customer, CustomerListDto.class)).collect(Collectors.toList());

        return new SuccessDataResult<List<CustomerListDto>>(response, BusinessMessages.CUSTOMERS_LISTED);
    }

    @Override
    public DataResult<GetCustomerDto> getByUserId(Integer id) {

        checkIfCustomerIdExists(id);

        Customer customer = this.customerDao.getById(id);

        GetCustomerDto response = this.modelMapperService.forDto().map(customer, GetCustomerDto.class);

        return new SuccessDataResult<GetCustomerDto>(response, BusinessMessages.CUSTOMER_FOUND_BY_ID);
    }

    @Override
    public void checkIfCustomerIdExists(Integer id) {

        if (!this.customerDao.existsById(id)) {

            throw new CustomerNotFoundException(BusinessMessages.CUSTOMER_NOT_FOUND);
        }
    }
}
