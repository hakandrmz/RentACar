package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.CustomerService;
import com.turkcell.rentacar.business.abstracts.InvoiceService;
import com.turkcell.rentacar.business.abstracts.OrderedServiceService;
import com.turkcell.rentacar.business.abstracts.RentService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.dtos.invoice.GetInvoiceDto;
import com.turkcell.rentacar.business.dtos.invoice.InvoiceListDto;
import com.turkcell.rentacar.business.requests.Invoice.CreateInvoiceRequest;
import com.turkcell.rentacar.business.requests.Invoice.DeleteInvoiceRequest;
import com.turkcell.rentacar.business.requests.Invoice.UpdateInvoiceRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.exceptions.invoice.InvoiceNotFoundException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.InvoiceDao;
import com.turkcell.rentacar.entities.concretes.Invoice;
import com.turkcell.rentacar.entities.concretes.Rental;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class InvoiceManager implements InvoiceService {

    private final InvoiceDao invoiceDao;
    private final ModelMapperService modelMapperService;
    private final RentService rentService;
    private final OrderedServiceService orderedServiceService;
    private final CustomerService customerService;

    @Autowired
    public InvoiceManager(InvoiceDao invoiceDao, ModelMapperService modelMapperService, RentService rentService,
                          OrderedServiceService orderedServiceService, CustomerService customerService) {

        this.invoiceDao = invoiceDao;
        this.modelMapperService = modelMapperService;
        this.rentService = rentService;
        this.orderedServiceService = orderedServiceService;
        this.customerService = customerService;
    }

    @Override
    public DataResult<List<InvoiceListDto>> getAll() {

        List<Invoice> result = this.invoiceDao.findAll();

        List<InvoiceListDto> response = result.stream()
                .map(invoice -> this.modelMapperService.forDto().map(invoice, InvoiceListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<InvoiceListDto>>(response, BusinessMessages.INVOICES_LISTED);
    }

    @Override
    public DataResult<Invoice> addForIndividualCustomers(CreateInvoiceRequest createInvoiceRequest) {

        Invoice invoice = this.modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);

        invoice.setInvoiceId(0);

        setInvoiceFields(createInvoiceRequest.getRentRentId(), invoice);
        invoice.setTotalPrice(calculateTotalPriceForIndividualCustomers(createInvoiceRequest.getRentRentId()));

        this.invoiceDao.save(invoice);

        return new SuccessDataResult<Invoice>(invoice, BusinessMessages.INVOICE_ADDED);
    }

    @Override
    public DataResult<Invoice> addForCorporateCustomers(CreateInvoiceRequest createInvoiceRequest) {

        Invoice invoice = this.modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);

        invoice.setInvoiceId(0);

        setInvoiceFields(createInvoiceRequest.getRentRentId(), invoice);
        invoice.setTotalPrice(calculateTotalPriceForCorporateCustomers(createInvoiceRequest.getRentRentId()));

        this.invoiceDao.save(invoice);

        return new SuccessDataResult<Invoice>(invoice, BusinessMessages.INVOICE_ADDED);
    }

    @Override
    public DataResult<Invoice> addExtraInvoice(int rentId, double totalPrice) {

        CreateInvoiceRequest createInvoiceRequest = new CreateInvoiceRequest();

        createInvoiceRequest.setRentRentId(rentId);

        Invoice invoice = this.modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);

        invoice.setInvoiceId(0);

        setInvoiceFields(rentId, invoice);

        invoice.setTotalPrice(totalPrice);

        this.invoiceDao.save(invoice);

        return new SuccessDataResult<Invoice>(invoice, BusinessMessages.INVOICE_ADDED);
    }

    @Override
    public DataResult<GetInvoiceDto> getById(Integer id) {

        checkIfInvoiceIdExists(id);

        Invoice invoice = this.invoiceDao.getById(id);

        GetInvoiceDto response = this.modelMapperService.forDto().map(invoice, GetInvoiceDto.class);

        return new SuccessDataResult<GetInvoiceDto>(response, BusinessMessages.INVOICE_FOUND_BY_ID);
    }

    @Override
    public Result update(UpdateInvoiceRequest updateInvoiceRequest) {

        checkIfInvoiceIdExists(updateInvoiceRequest.getInvoiceId());

        Invoice invoice = this.modelMapperService.forRequest().map(updateInvoiceRequest, Invoice.class);

        setInvoiceFields(updateInvoiceRequest.getRentRentId(), invoice);

        this.invoiceDao.save(invoice);

        return new SuccessResult(BusinessMessages.INVOICE_UPDATED);
    }

    @Override
    public Result delete(int invoiceId) {

        checkIfInvoiceIdExists(invoiceId);

        this.invoiceDao.deleteById(invoiceId);

        return new SuccessResult(BusinessMessages.INVOICE_DELETED);
    }

    @Override
    public DataResult<List<InvoiceListDto>> getByCustomerUserId(Integer id) {

        this.customerService.checkIfCustomerIdExists(id);

        List<Invoice> result = this.invoiceDao.findByCustomerUserId(id);

        List<InvoiceListDto> response = result.stream()
                .map(invoice -> this.modelMapperService.forDto().map(invoice, InvoiceListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<InvoiceListDto>>(response, BusinessMessages.INVOICES_LISTED_BY_CUSTOMER_ID);
    }

    @Override
    public DataResult<List<InvoiceListDto>> getByRentId(Integer id) {

        this.rentService.checkIfRentIdExists(id);

        List<Invoice> result = this.invoiceDao.findByRentalRentalId(id);

        List<InvoiceListDto> response = result.stream().map(invoice -> this.modelMapperService.forDto()
                .map(invoice, InvoiceListDto.class)).collect(Collectors.toList());

        return new SuccessDataResult<List<InvoiceListDto>>(response, BusinessMessages.INVOICES_LISTED_BY_RENT_ID);
    }

    @Override
    public DataResult<List<InvoiceListDto>> findByCreationDateBetween(LocalDate startDate, LocalDate endDate) {

        List<Invoice> result = this.invoiceDao.findAllByCreationDateBetween(startDate, endDate);

        List<InvoiceListDto> response = result.stream()
                .map(invoice -> this.modelMapperService.forDto().map(invoice, InvoiceListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<InvoiceListDto>>(response, BusinessMessages.INVOICES_LISTED_BY_DATES);
    }

    @Override
    public void checkIfInvoiceIdExists(Integer id) {

        if (!this.invoiceDao.existsById(id)) {

            throw new InvoiceNotFoundException(BusinessMessages.INVOICE_NOT_FOUND);
        }
    }

    @Override
    public double calculateTotalPriceForIndividualCustomers(int rentId) {

        double rentPrice = this.rentService.calculateRentPrice(rentId);

        double orderedServicePrice = this.orderedServiceService.calculateOrderedServicePrice(rentId);

        double totalInvoicePrice = rentPrice + orderedServicePrice;

        return totalInvoicePrice;
    }

    @Override
    public double calculateTotalPriceForCorporateCustomers(int rentId) {

        double rentPrice = this.rentService.calculateRentPrice(rentId);

        double orderedServicePrice = this.orderedServiceService.calculateOrderedServicePrice(rentId);

        double totalInvoicePrice = rentPrice + orderedServicePrice;

        return totalInvoicePrice;
    }

    @Override
    public void setInvoiceFields(int rentId, Invoice invoice) {

        Rental rental = this.rentService.bringRentById(rentId);

        invoice.setCreationDate(LocalDate.now());

        invoice.setRentalStartDate(rental.getRentalReturnDate());

        invoice.setRentalReturnDate(rental.getRentalReturnDate());

        invoice.setTotalRentDay((int) ChronoUnit.DAYS.between(rental.getRentStartDate(), rental.getRentalReturnDate()) + 1);

        invoice.setCustomer(rental.getCustomer());

        invoice.setInvoiceNumber(UUID.randomUUID().toString());
    }
}
