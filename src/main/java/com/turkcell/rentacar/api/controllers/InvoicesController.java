package com.turkcell.rentacar.api.controllers;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import com.turkcell.rentacar.business.abstracts.InvoiceService;
import com.turkcell.rentacar.business.dtos.invoice.GetInvoiceDto;
import com.turkcell.rentacar.business.dtos.invoice.InvoiceListDto;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;


@RestController
@RequestMapping("/api/invoices")
public class InvoicesController {

    private final InvoiceService invoiceService;

    @Autowired
    public InvoicesController(InvoiceService invoiceService) {

        this.invoiceService = invoiceService;
    }

    @GetMapping("/getAll")
    DataResult<List<InvoiceListDto>> getAll() {

        return this.invoiceService.getAll();
    }

    @GetMapping("/getById/{invoiceId}")
    DataResult<GetInvoiceDto> getById(@PathVariable("invoiceId") Integer id) throws BusinessException {

        return this.invoiceService.getById(id);
    }

    @GetMapping("/getByCustomerUserId/{customerUserId}")
    DataResult<List<InvoiceListDto>> getByCustomerUserId(@PathVariable("customerUserId") Integer id) throws BusinessException {

        return this.invoiceService.getByCustomerUserId(id);
    }

    @GetMapping("/getByRentId/{rentId}")
    DataResult<List<InvoiceListDto>> getByRentId(@PathVariable("rentId") Integer id) throws BusinessException {

        return this.invoiceService.getByRentId(id);
    }

    @GetMapping(value = "/findByCreationDateBetween/{startDate}/{endDate}")
    DataResult<List<InvoiceListDto>> findByCreationDateBetween(
            @PathVariable("startDate")
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("endDate") LocalDate endDate) {

        return this.invoiceService.findByCreationDateBetween(startDate, endDate);
    }

    @DeleteMapping("/delete")
    Result delete(@RequestBody @Valid int id) throws BusinessException {

        return this.invoiceService.delete(id);
    }
}
