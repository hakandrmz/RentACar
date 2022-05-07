package com.turkcell.rentacar.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.turkcell.rentacar.business.abstracts.CustomerService;
import com.turkcell.rentacar.business.dtos.customer.CustomerListDto;
import com.turkcell.rentacar.business.dtos.customer.GetCustomerDto;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;

@RestController
@RequestMapping("/api/customers")
public class CustomersController {

    private final CustomerService customerService;

    @Autowired
    public CustomersController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/getAll")
    DataResult<List<CustomerListDto>> getAll() {

        return this.customerService.getAll();
    }

    @GetMapping("getByUserId/{userId}")
    DataResult<GetCustomerDto> getByUserId(@PathVariable("userId") int id) throws BusinessException {

        return this.customerService.getByUserId(id);
    }
}
