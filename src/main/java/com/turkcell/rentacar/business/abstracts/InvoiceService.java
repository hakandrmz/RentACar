package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.business.dtos.invoice.InvoiceByIdDto;
import com.turkcell.rentacar.business.dtos.invoice.InvoiceListDto;
import com.turkcell.rentacar.business.requests.invoice.CreateInvoiceRequest;
import com.turkcell.rentacar.business.requests.invoice.UpdateInvoiceRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.entities.concretes.Invoice;

import java.time.LocalDate;
import java.util.List;

public interface InvoiceService {
    DataResult<List<InvoiceListDto>> getAll();

    Result add(CreateInvoiceRequest createInvoiceRequest);

    DataResult<InvoiceByIdDto> getById(int id);

    Result update(UpdateInvoiceRequest updateInvoiceRequest);

    Result deleteById(int invoiceId);

    DataResult<List<InvoiceListDto>> getAllInvoiceByCustomerId(int id);

    DataResult<List<InvoiceListDto>> getAllInvoicesBetweenTwoDates(LocalDate from, LocalDate to);

    Invoice createInvoiceForSave(CreateInvoiceRequest createInvoiceRequest);
}
