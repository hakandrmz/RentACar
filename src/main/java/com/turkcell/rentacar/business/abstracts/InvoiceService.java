package com.turkcell.rentacar.business.abstracts;

import java.time.LocalDate;
import java.util.List;

import com.turkcell.rentacar.business.dtos.invoice.GetInvoiceDto;
import com.turkcell.rentacar.business.dtos.invoice.InvoiceListDto;
import com.turkcell.rentacar.business.requests.Invoice.CreateInvoiceRequest;
import com.turkcell.rentacar.business.requests.Invoice.DeleteInvoiceRequest;
import com.turkcell.rentacar.business.requests.Invoice.UpdateInvoiceRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.entities.concretes.Invoice;

public interface InvoiceService {

    DataResult<List<InvoiceListDto>> getAll();

    DataResult<Invoice> addForIndividualCustomers(CreateInvoiceRequest createInvoiceRequest);

    DataResult<Invoice> addForCorporateCustomers(CreateInvoiceRequest createInvoiceRequest);

    DataResult<Invoice> addExtraInvoice(int rentId, double totalPrice);

    DataResult<GetInvoiceDto> getById(Integer id);

    Result update(UpdateInvoiceRequest updateInvoiceRequest);

    Result delete(int id);

    DataResult<List<InvoiceListDto>> getByCustomerUserId(Integer id);

    DataResult<List<InvoiceListDto>> getByRentId(Integer id);

    DataResult<List<InvoiceListDto>> findByCreationDateBetween(LocalDate startDate, LocalDate endDate);

    void checkIfInvoiceIdExists(Integer id);

    double calculateTotalPriceForIndividualCustomers(int rentId);

    double calculateTotalPriceForCorporateCustomers(int rentId);

    void setInvoiceFields(int rentId, Invoice invoice);


}
