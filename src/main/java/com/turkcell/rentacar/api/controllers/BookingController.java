package com.turkcell.rentacar.api.controllers;

import com.turkcell.rentacar.api.models.CreateRentModelForCorporateCustomers;
import com.turkcell.rentacar.api.models.CreateRentModelForIndividualCustomers;
import com.turkcell.rentacar.api.models.EndRentWithExtraPaymentModel;
import com.turkcell.rentacar.business.abstracts.BookingService;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService transactionalRentService) {

        this.bookingService = transactionalRentService;
    }

    @PostMapping("/createRentForIndividualCustomers")
    Result createRentForIndividualCustomers(@RequestBody @Valid CreateRentModelForIndividualCustomers createRentModelForIndividualCustomers)
            throws BusinessException {

        return this.bookingService.createRentForIndividualCustomers(createRentModelForIndividualCustomers);
    }

    @PostMapping("/createRentForCorporateCustomers")
    Result createRentForCorporateCustomers(@RequestBody @Valid CreateRentModelForCorporateCustomers createRentModelForCorporateCustomers)
            throws BusinessException {

        return this.bookingService.createRentForCorporateCustomers(createRentModelForCorporateCustomers);
    }

    @PostMapping("/endRentWithExtraPayment")
    Result endRentWithExtraPayment(@RequestBody @Valid EndRentWithExtraPaymentModel endRentWithExtraPaymentModel) throws BusinessException {

        return this.bookingService.endRentWithExtraPayment(endRentWithExtraPaymentModel);
    }

}
