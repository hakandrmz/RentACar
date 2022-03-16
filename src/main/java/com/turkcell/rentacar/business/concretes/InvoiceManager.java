package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.InvoiceService;
import com.turkcell.rentacar.business.abstracts.RentalService;
import com.turkcell.rentacar.business.dtos.city.CityByIdDto;
import com.turkcell.rentacar.business.dtos.city.CityListDto;
import com.turkcell.rentacar.business.dtos.color.ColorListDto;
import com.turkcell.rentacar.business.dtos.invoice.InvoiceByIdDto;
import com.turkcell.rentacar.business.dtos.invoice.InvoiceListDto;
import com.turkcell.rentacar.business.requests.city.CreateCityRequest;
import com.turkcell.rentacar.business.requests.city.UpdateCityRequest;
import com.turkcell.rentacar.business.requests.invoice.CreateInvoiceRequest;
import com.turkcell.rentacar.business.requests.invoice.UpdateInvoiceRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.InvoiceDao;
import com.turkcell.rentacar.entities.concretes.Color;
import com.turkcell.rentacar.entities.concretes.Invoice;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceManager implements InvoiceService {

    private final ModelMapperService modelMapperService;
    private final RentalService rentalService;
    private final InvoiceDao invoiceDao;

    public InvoiceManager(ModelMapperService modelMapperService, InvoiceDao invoiceDao, RentalService rentalService) {
        this.modelMapperService = modelMapperService;
        this.invoiceDao = invoiceDao;
        this.rentalService = rentalService;
    }

    @Override
    public DataResult<List<InvoiceListDto>> getAll() {

        List<Invoice> result = this.invoiceDao.findAll();

        List<InvoiceListDto> response = result.stream()
                .map(invoice -> this.modelMapperService.forDto().map(invoice, InvoiceListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<InvoiceListDto>>(response, "Invoices are listed successfully.");

    }

    @Override
    public Result add(CreateInvoiceRequest createInvoiceRequest) {
        return null;
    }

    @Override
    public DataResult<InvoiceByIdDto> getById(int id) {

        this.checkIfInvoiceIsExist(id);

        Invoice invoice = invoiceDao.getById(id);

        InvoiceByIdDto response = modelMapperService.forDto().map(invoice, InvoiceByIdDto.class);

        return new SuccessDataResult<>(response);
    }

    @Override
    public Result update(UpdateInvoiceRequest updateInvoiceRequest) {
        return null;
    }

    @Override
    public Result deleteById(int invoiceId) {

        this.checkIfInvoiceIsExist(invoiceId);

        return new SuccessResult("Invoice deleted with id: " + invoiceId);
    }

    @Override
    public DataResult<List<InvoiceListDto>> getAllInvoiceByCustomerId(int id) {
        return null;
    }

    @Override
    public DataResult<List<InvoiceListDto>> getAllInvoicesBetweenTwoDates(LocalDate from, LocalDate to) {
        return null;
    }

    private void checkIfInvoiceIsExist(int invoiceId) {
        if (!invoiceDao.existsById(invoiceId)) {
            throw new BusinessException("Invoice is not exist with id: " + invoiceId);
        }
    }

    private void checkIfCustomerIsExist(int customerId) {

    }
}
