package com.turkcell.rentacar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.turkcell.rentacar.business.abstracts.CorporateCustomerService;
import com.turkcell.rentacar.business.dtos.corporateCustomer.CorporateCustomerListDto;
import com.turkcell.rentacar.business.dtos.corporateCustomer.GetCorporateCustomerDto;
import com.turkcell.rentacar.business.requests.corporateCustomer.CreateCorporateCustomerRequest;
import com.turkcell.rentacar.business.requests.corporateCustomer.UpdateCorporateCustomerRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/corporateCustomers")
public class CorporateCustomersController {

    private final CorporateCustomerService corporateCustomerService;

    @Autowired
    public CorporateCustomersController(CorporateCustomerService corporateCustomerService) {

        this.corporateCustomerService = corporateCustomerService;
    }

    @GetMapping("/getAll")
    DataResult<List<CorporateCustomerListDto>> getAll() {

        return this.corporateCustomerService.getAll();
    }

    @PostMapping("/add")
    Result add(@RequestBody @Valid CreateCorporateCustomerRequest createCorporateCustomerRequest) throws BusinessException {

        return this.corporateCustomerService.add(createCorporateCustomerRequest);
    }

    @GetMapping("/getByUserId/{userId}")
    DataResult<GetCorporateCustomerDto> getByUserId(@PathVariable("userId") Integer id) throws BusinessException {

        return this.corporateCustomerService.getByUserId(id);
    }

    @PutMapping("/update")
    Result update(@RequestBody @Valid UpdateCorporateCustomerRequest updateCorporateCustomerRequest) throws BusinessException {

        return this.corporateCustomerService.update(updateCorporateCustomerRequest);
    }

    @DeleteMapping("/delete")
    Result delete(@RequestBody @Valid int id) throws BusinessException {

        return this.corporateCustomerService.delete(id);
    }
}
