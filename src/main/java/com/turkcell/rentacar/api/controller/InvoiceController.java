package com.turkcell.rentacar.api.controller;

import com.turkcell.rentacar.business.abstracts.InvoiceService;
import com.turkcell.rentacar.business.dtos.invoice.InvoiceByIdDto;
import com.turkcell.rentacar.business.dtos.invoice.InvoiceListDto;
import com.turkcell.rentacar.business.requests.invoice.CreateInvoiceRequest;
import com.turkcell.rentacar.business.requests.invoice.UpdateInvoiceRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/getAll")
    public DataResult<List<InvoiceListDto>> getAll() {
        return this.invoiceService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateInvoiceRequest createInvoiceRequest) {

        return this.invoiceService.add(createInvoiceRequest);
    }

    @GetMapping("/getById")
    public DataResult<InvoiceByIdDto> getById(@RequestParam int invoiceId) {
        return this.invoiceService.getById(invoiceId);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Valid UpdateInvoiceRequest updateInvoiceRequest) {
        return this.invoiceService.update(updateInvoiceRequest);
    }

    @DeleteMapping("/deleteById")
    public Result deleteById(@RequestParam int id) {
        return this.invoiceService.deleteById(id);
    }


}
