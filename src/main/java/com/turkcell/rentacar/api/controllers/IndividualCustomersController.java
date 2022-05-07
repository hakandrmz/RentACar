package com.turkcell.rentacar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.turkcell.rentacar.business.abstracts.IndividualCustomerService;
import com.turkcell.rentacar.business.dtos.individualCustomer.GetIndividualCustomerDto;
import com.turkcell.rentacar.business.dtos.individualCustomer.IndividualCustomerListDto;
import com.turkcell.rentacar.business.requests.individualCustomer.CreateIndividualCustomerRequest;
import com.turkcell.rentacar.business.requests.individualCustomer.UpdateIndividualCustomerRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/individualCustomers")
public class IndividualCustomersController {

    private final IndividualCustomerService individualCustomerService;

    @Autowired
    public IndividualCustomersController(IndividualCustomerService individualCustomerService) {

        this.individualCustomerService = individualCustomerService;
    }

    @GetMapping("/getAll")
    DataResult<List<IndividualCustomerListDto>> getAll() {

        return this.individualCustomerService.getAll();
    }

    @PostMapping("/add")
    Result add(@RequestBody @Valid CreateIndividualCustomerRequest createIndividualCustomerRequest) throws BusinessException {

        return this.individualCustomerService.add(createIndividualCustomerRequest);
    }

    @GetMapping("/getByUserId/{id}")
    DataResult<GetIndividualCustomerDto> getByUserId(@PathVariable("userId") int id) throws BusinessException {

        return this.individualCustomerService.getByUserId(id);
    }

    @PutMapping("/update")
    Result update(@RequestBody @Valid UpdateIndividualCustomerRequest updateIndividualCustomerRequest) throws BusinessException {

        return this.individualCustomerService.update(updateIndividualCustomerRequest);
    }

    @DeleteMapping("/delete")
    Result delete(@RequestBody @Valid int id) throws BusinessException {

        return this.individualCustomerService.delete(id);
    }
}
