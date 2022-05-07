package com.turkcell.rentacar.api.controllers;

import com.turkcell.rentacar.business.abstracts.CreditCardService;
import com.turkcell.rentacar.business.dtos.creditCard.CreditCardListDto;
import com.turkcell.rentacar.business.dtos.creditCard.GetCreditCardDto;
import com.turkcell.rentacar.business.requests.creditCard.CreateCreditCardRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/creditCards")
public class CreditCardsController {

    private final CreditCardService creditCardService;


    @Autowired
    public CreditCardsController(CreditCardService creditCardService) {

        this.creditCardService = creditCardService;
    }

    @PostMapping("/add")
    Result add(@RequestBody @Valid CreateCreditCardRequest createCreditCardRequest) {

        return this.creditCardService.add(createCreditCardRequest);
    }

    @GetMapping("/getAll")
    DataResult<List<CreditCardListDto>> getAll() {

        return this.creditCardService.getAll();
    }

    @GetMapping("/getById/{id}")
    DataResult<GetCreditCardDto> getById(@PathVariable("id") Integer id) throws BusinessException {

        return this.creditCardService.getById(id);
    }

    @GetMapping("/getByCustomerUserId/{customerUserId}")
    DataResult<List<CreditCardListDto>> getByCustomerUserId(@PathVariable("customerUserId") int customerUserId) throws BusinessException {

        return this.creditCardService.getByCustomerUserId(customerUserId);
    }
}
