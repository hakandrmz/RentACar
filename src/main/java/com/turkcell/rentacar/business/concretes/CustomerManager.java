package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.CustomerService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.dtos.customer.CustomerListDto;
import com.turkcell.rentacar.business.dtos.customer.GetCustomerDto;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.dataaccess.abstracts.CustomerDao;
import com.turkcell.rentacar.entities.concretes.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerManager implements CustomerService {

    private final CustomerDao customerDao;
    private final ModelMapperService modelMapperService;

    @Override
    public DataResult<List<CustomerListDto>> getAll() {

        List<Customer> result = this.customerDao.findAll();

        List<CustomerListDto> response = result.stream().map(customer -> this.modelMapperService
                .forDto().map(customer, CustomerListDto.class)).collect(Collectors.toList());

        return new SuccessDataResult<>(response, BusinessMessages.SUCCESSFULLY_LISTED);
    }

    @Override
    public DataResult<GetCustomerDto> getByUserId(Integer id) throws BusinessException {

        checkIfCustomerIdExists(id);

        Customer customer = this.customerDao.getById(id);

        GetCustomerDto response = this.modelMapperService.forDto().map(customer, GetCustomerDto.class);

        return new SuccessDataResult<>(response, BusinessMessages.SUCCESSFULLY_FOUND);
    }

    @Override
    public void checkIfCustomerIdExists(Integer id) throws BusinessException {

        if (!this.customerDao.existsById(id)) {

            throw new BusinessException(BusinessMessages.NOT_FOUND);
        }
    }
}
