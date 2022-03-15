package com.turkcell.rentacar.api.controller;


import com.turkcell.rentacar.business.abstracts.CorporateCustomerService;
import com.turkcell.rentacar.business.dtos.customerDtos.CorporateCustomerByIdDto;
import com.turkcell.rentacar.business.dtos.customerDtos.CorporateCustomerListDto;
import com.turkcell.rentacar.business.requests.customer.corporateCustomer.CreateCorporateCustomerRequest;
import com.turkcell.rentacar.business.requests.customer.corporateCustomer.UpdateCorporateCustomerRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/corporateCustomers")
public class CorporateCustomerController {

    private final CorporateCustomerService corporateCustomerService;

    public CorporateCustomerController(CorporateCustomerService corporateCustomerService) {

        this.corporateCustomerService = corporateCustomerService;
    }

    @GetMapping("/getAll")
    public DataResult<List<CorporateCustomerListDto>> getAll() {

        return this.corporateCustomerService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateCorporateCustomerRequest createCorporateCustomerRequest) {

        return this.corporateCustomerService.add(createCorporateCustomerRequest);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Valid UpdateCorporateCustomerRequest updateCorporateCustomerRequest) {

        return this.corporateCustomerService.update(updateCorporateCustomerRequest);
    }

    @DeleteMapping("/delete")
    Result delete(@RequestParam int corporateCustomerId) {

        return this.corporateCustomerService.delete(corporateCustomerId);
    }

    @GetMapping("/getCorporateCustomerById")
    public DataResult<CorporateCustomerByIdDto> getCorporateCustomerByUserId(int userId) {

        return this.corporateCustomerService.getCorporateCustomerByUserId(userId);
    }
}
