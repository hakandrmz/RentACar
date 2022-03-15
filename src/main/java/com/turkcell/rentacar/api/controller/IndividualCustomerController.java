package com.turkcell.rentacar.api.controller;


import com.turkcell.rentacar.business.abstracts.IndividualCustomerService;
import com.turkcell.rentacar.business.dtos.customerDtos.IndividualCustomerByIdDto;
import com.turkcell.rentacar.business.dtos.customerDtos.IndividualCustomerListDto;
import com.turkcell.rentacar.business.requests.customer.individualCustomer.CreateIndividualCustomerRequest;
import com.turkcell.rentacar.business.requests.customer.individualCustomer.UpdateIndividualCustomerRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/individualCustomers")
public class IndividualCustomerController {

    private final IndividualCustomerService individualCustomerService;

    public IndividualCustomerController(IndividualCustomerService individualCustomerService) {

        this.individualCustomerService = individualCustomerService;
    }

    @GetMapping("/getAll")
    public DataResult<List<IndividualCustomerListDto>> getAll() {

        return this.individualCustomerService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateIndividualCustomerRequest createIndividualCustomerRequest) {

        return this.individualCustomerService.add(createIndividualCustomerRequest);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Valid UpdateIndividualCustomerRequest updateIndividualCustomerRequest) {

        return this.individualCustomerService.update(updateIndividualCustomerRequest);
    }

    @DeleteMapping("/delete")
    Result delete(@RequestParam int deleteIndividualCustomerRequest) {

        return this.individualCustomerService.delete(deleteIndividualCustomerRequest);
    }

    @GetMapping("/getIndividualCustomerById")
    public DataResult<IndividualCustomerByIdDto> getIndividualCustomerByUserId(int userId) {

        return this.individualCustomerService.getIndividualCustomerByIdDtoByUserId(userId);
    }
}

