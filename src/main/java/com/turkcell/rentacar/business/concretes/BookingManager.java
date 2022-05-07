package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.api.models.CreateRentModelForCorporateCustomers;
import com.turkcell.rentacar.api.models.CreateRentModelForIndividualCustomers;
import com.turkcell.rentacar.api.models.EndRentWithExtraPaymentModel;
import com.turkcell.rentacar.business.abstracts.*;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.requests.Invoice.CreateInvoiceRequest;
import com.turkcell.rentacar.business.requests.creditCard.CreateCreditCardRequest;
import com.turkcell.rentacar.business.requests.orderedService.CreateOrderedServiceRequest;
import com.turkcell.rentacar.business.requests.payment.CreatePaymentRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.entities.concretes.Invoice;
import com.turkcell.rentacar.entities.concretes.Rent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingManager implements BookingService {

    private final RentService rentService;
    private final OrderedServiceService orderedServiceService;
    private final InvoiceService invoiceService;
    private final PaymentService paymentService;
    private final CreditCardService creditCardService;
    private final CarService carService;
    private final CityService cityService;
    private final AdditionalServiceService additionalServiceService;
    private final IndividualCustomerService individualCustomerService;

    @Autowired
    public BookingManager(RentService rentService, OrderedServiceService orderedServiceService,
                          InvoiceService invoiceService, PaymentService paymentService, CreditCardService creditCardService,
                          CarService carService, CityService cityService, AdditionalServiceService additionalServiceService,
                          IndividualCustomerService individualCustomerService) {

        this.rentService = rentService;
        this.orderedServiceService = orderedServiceService;
        this.invoiceService = invoiceService;
        this.paymentService = paymentService;
        this.creditCardService = creditCardService;
        this.carService = carService;
        this.cityService = cityService;
        this.additionalServiceService = additionalServiceService;
        this.individualCustomerService = individualCustomerService;
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public Result createRentForIndividualCustomers(CreateRentModelForIndividualCustomers createRentModelForIndividualCustomers)
            throws BusinessException {

        this.carService.checkIfCarIdExists(createRentModelForIndividualCustomers.getCreateRentRequest().getCarId());
        this.cityService.checkIfCityIdExists(createRentModelForIndividualCustomers.getCreateRentRequest().getRentCityId());
        this.cityService.checkIfCityIdExists(createRentModelForIndividualCustomers.getCreateRentRequest().getReturnCityId());
        this.individualCustomerService.checkIfIndividualCustomerIdExists(createRentModelForIndividualCustomers.getCreateRentRequest().getCustomerUserId());
        this.additionalServiceService.checkIfAdditionalServiceIdExistsOnOrderedServiceList(createRentModelForIndividualCustomers.getCreateOrderedServiceRequests());

        Rent rent = this.rentService.add(createRentModelForIndividualCustomers.getCreateRentRequest()).getData();

        addOrderedServicesToRent(createRentModelForIndividualCustomers.getCreateOrderedServiceRequests(), rent.getRentId());

        CreateInvoiceRequest createInvoiceRequest = new CreateInvoiceRequest();

        createInvoiceRequest.setRentRentId(rent.getRentId());

        Invoice invoice = this.invoiceService.addForIndividualCustomers(createInvoiceRequest).getData();

        setCreatePaymentRequestFields(createRentModelForIndividualCustomers.getCreatePaymentRequest(), rent, invoice);

        this.paymentService.add(createRentModelForIndividualCustomers.getCreatePaymentRequest());

        userWantsToSaveCreditCard(createRentModelForIndividualCustomers.getSaveCardOption(),
                createRentModelForIndividualCustomers.getCreatePaymentRequest());

        return new SuccessResult(BusinessMessages.SUCCESSFULLY_ADDED + invoice.getTotalPrice());
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public Result createRentForCorporateCustomers(CreateRentModelForCorporateCustomers createRentModelForCorporateCustomers)
            throws BusinessException {

        Rent rent = this.rentService.add(createRentModelForCorporateCustomers.getCreateRentRequest()).getData();

        addOrderedServicesToRent(createRentModelForCorporateCustomers.getCreateOrderedServiceRequests(), rent.getRentId());

        CreateInvoiceRequest createInvoiceRequest = new CreateInvoiceRequest();

        createInvoiceRequest.setRentRentId(rent.getRentId());

        Invoice invoice = this.invoiceService.addForCorporateCustomers(createInvoiceRequest).getData();

        setCreatePaymentRequestFields(createRentModelForCorporateCustomers.getCreatePaymentRequest(), rent, invoice);

        this.paymentService.add(createRentModelForCorporateCustomers.getCreatePaymentRequest());

        userWantsToSaveCreditCard(createRentModelForCorporateCustomers.getSaveCardOption(),
                createRentModelForCorporateCustomers.getCreatePaymentRequest());

        return new SuccessResult(BusinessMessages.SUCCESSFULLY_ADDED + invoice.getTotalPrice());
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public Result endRentWithExtraPayment(EndRentWithExtraPaymentModel endRentWithExtraPaymentModel) throws BusinessException {

        this.rentService.checkIfRentIdExists(endRentWithExtraPaymentModel.getEndRentRequest().getRentId());

        Rent rent = this.rentService.bringRentById(endRentWithExtraPaymentModel.getEndRentRequest().getRentId());

        this.rentService.checkIfRentAlreadyEnded(rent);

        double totalPrice = this.rentService.calculateExtraDaysPrice(rent.getRentId(), LocalDate.now());

        checkIfExtraPaymentNeeded(totalPrice);

        rent.setRentReturnDate(LocalDate.now());

        Invoice invoice = this.invoiceService.addExtraInvoice(rent.getRentId(), totalPrice).getData();

        setCreatePaymentRequestFields(endRentWithExtraPaymentModel.getCreatePaymentRequest(), rent, invoice);

        this.paymentService.add(endRentWithExtraPaymentModel.getCreatePaymentRequest());

        this.rentService.endRent(endRentWithExtraPaymentModel.getEndRentRequest());

        userWantsToSaveCreditCard(endRentWithExtraPaymentModel.getCardSaveOption(),
                endRentWithExtraPaymentModel.getCreatePaymentRequest());

        return new SuccessResult(BusinessMessages.SUCCESSFULLY_ADDED + totalPrice);
    }

    @Override
    public void addOrderedServicesToRent(List<CreateOrderedServiceRequest> createOrderedServiceRequests, int rentId) {

        if (createOrderedServiceRequests == null) {

            return;
        }

        for (CreateOrderedServiceRequest createOrderedServiceRequest : createOrderedServiceRequests) {

            createOrderedServiceRequest.setRentId(rentId);

            this.orderedServiceService.add(createOrderedServiceRequest);
        }
    }

    @Override
    public void userWantsToSaveCreditCard(String saveCardOption, CreatePaymentRequest createPaymentRequest) throws BusinessException {

        if (saveCardOption.equalsIgnoreCase("yes")) {

            CreateCreditCardRequest createCreditCardRequest = new CreateCreditCardRequest();

            createCreditCardRequest.setCreditCardNo(createPaymentRequest.getCreatePosRequest().getCreditCardNo());
            createCreditCardRequest.setCreditCardHolder(createPaymentRequest.getCreatePosRequest().getCreditCardHolder());
            createCreditCardRequest.setExpirationMonth(createPaymentRequest.getCreatePosRequest().getExpirationMonth());
            createCreditCardRequest.setExpirationYear(createPaymentRequest.getCreatePosRequest().getExpirationYear());
            createCreditCardRequest.setCvv(createPaymentRequest.getCreatePosRequest().getCvv());
            createCreditCardRequest.setCustomerUserId(createPaymentRequest.getCustomerUserId());

            this.creditCardService.add(createCreditCardRequest);
        }
    }

    @Override
    public void checkIfExtraPaymentNeeded(double price) throws BusinessException {

        if (!(price > 0)) {

            throw new BusinessException(BusinessMessages.NO_NEED_FOR_EXTRA_PAYMENT);
        }
    }

    @Override
    public void setCreatePaymentRequestFields(CreatePaymentRequest createPaymentRequest, Rent rent, Invoice invoice) {

        createPaymentRequest.setRentId(rent.getRentId());
        createPaymentRequest.setInvoiceId(invoice.getInvoiceId());
        createPaymentRequest.setCustomerUserId(rent.getCustomer().getUserId());
        createPaymentRequest.setPaymentAmount(invoice.getTotalPrice());
    }


}
