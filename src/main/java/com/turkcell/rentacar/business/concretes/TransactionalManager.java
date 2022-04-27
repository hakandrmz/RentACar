package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.api.models.*;
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
import com.turkcell.rentacar.entities.concretes.Rental;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionalManager implements TransactionalService {

    private final RentService rentService;
    private final OrderedServiceService orderedServiceService;
    private final InvoiceService invoiceService;
    private final PaymentService paymentService;
    private final CreditCardService creditCardService;
    private final CarService carService;
    private final CityService cityService;
    private final AdditionalServiceService additionalServiceService;
    private final IndividualCustomerService individualCustomerService;
    private final CorporateCustomerService corporateCustomerService;

    @Autowired
    public TransactionalManager(RentService rentService, OrderedServiceService orderedServiceService,
                                InvoiceService invoiceService, PaymentService paymentService, CreditCardService creditCardService,
                                CarService carService, CityService cityService, AdditionalServiceService additionalServiceService,
                                IndividualCustomerService individualCustomerService, CorporateCustomerService corporateCustomerService) {

        this.rentService = rentService;
        this.orderedServiceService = orderedServiceService;
        this.invoiceService = invoiceService;
        this.paymentService = paymentService;
        this.creditCardService = creditCardService;
        this.carService = carService;
        this.cityService = cityService;
        this.additionalServiceService = additionalServiceService;
        this.individualCustomerService = individualCustomerService;
        this.corporateCustomerService = corporateCustomerService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result createRentForIndividualCustomers(CreateRentModelForIndividualCustomers createRentModelForIndividualCustomers)
            throws BusinessException {

        this.carService.checkIfCarIdExists(createRentModelForIndividualCustomers.getCreateRentRequest().getCarId());
        this.cityService.checkIfCityIdExists(createRentModelForIndividualCustomers.getCreateRentRequest().getRentCityId());
        this.cityService.checkIfCityIdExists(createRentModelForIndividualCustomers.getCreateRentRequest().getReturnCityId());
        this.individualCustomerService.checkIfIndividualCustomerIdExists(createRentModelForIndividualCustomers.getCreateRentRequest().getCustomerUserId());
        this.additionalServiceService.checkIfAdditionalServiceIdExistsOnOrderedServiceList(createRentModelForIndividualCustomers.getCreateOrderedServiceRequests());

        Rental rental = this.rentService.add(createRentModelForIndividualCustomers.getCreateRentRequest()).getData();

        addOrderedServicesToRent(createRentModelForIndividualCustomers.getCreateOrderedServiceRequests(), rental.getRentalId());

        CreateInvoiceRequest createInvoiceRequest = new CreateInvoiceRequest();

        createInvoiceRequest.setRentRentId(rental.getRentalId());

        Invoice invoice = this.invoiceService.addForIndividualCustomers(createInvoiceRequest).getData();

        setCreatePaymentRequestFields(createRentModelForIndividualCustomers.getCreatePaymentRequest(), rental, invoice);

        this.paymentService.add(createRentModelForIndividualCustomers.getCreatePaymentRequest());

        checkIfUserWantsToSaveCreditCard(createRentModelForIndividualCustomers.getCreditCardSaveOption(),
                createRentModelForIndividualCustomers.getCreatePaymentRequest());

        return new SuccessResult(BusinessMessages.RENT_MODEL_SUCCESSFULL + invoice.getTotalPrice());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result createRentForCorporateCustomers(CreateRentModelForCorporateCustomers createRentModelForCorporateCustomers)
            throws BusinessException {

        this.carService.checkIfCarIdExists(createRentModelForCorporateCustomers.getCreateRentRequest().getCarId());
        this.cityService.checkIfCityIdExists(createRentModelForCorporateCustomers.getCreateRentRequest().getRentCityId());
        this.cityService.checkIfCityIdExists(createRentModelForCorporateCustomers.getCreateRentRequest().getReturnCityId());
        this.corporateCustomerService.checkIfCorporateCustomerIdExists(createRentModelForCorporateCustomers.getCreateRentRequest().getCustomerUserId());
        this.additionalServiceService.checkIfAdditionalServiceIdExistsOnOrderedServiceList(createRentModelForCorporateCustomers.getCreateOrderedServiceRequests());

        Rental rental = this.rentService.add(createRentModelForCorporateCustomers.getCreateRentRequest()).getData();

        addOrderedServicesToRent(createRentModelForCorporateCustomers.getCreateOrderedServiceRequests(), rental.getRentalId());

        CreateInvoiceRequest createInvoiceRequest = new CreateInvoiceRequest();

        createInvoiceRequest.setRentRentId(rental.getRentalId());

        Invoice invoice = this.invoiceService.addForCorporateCustomers(createInvoiceRequest).getData();

        setCreatePaymentRequestFields(createRentModelForCorporateCustomers.getCreatePaymentRequest(), rental, invoice);

        this.paymentService.add(createRentModelForCorporateCustomers.getCreatePaymentRequest());

        checkIfUserWantsToSaveCreditCard(createRentModelForCorporateCustomers.getCreditCardSaveOption(),
                createRentModelForCorporateCustomers.getCreatePaymentRequest());

        return new SuccessResult(BusinessMessages.RENT_MODEL_SUCCESSFULL + invoice.getTotalPrice());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result endRentWithExtraPayment(EndRentWithExtraPaymentModel endRentWithExtraPaymentModel) throws BusinessException {

        this.rentService.checkIfRentIdExists(endRentWithExtraPaymentModel.getEndRentRequest().getRentId());

        Rental rental = this.rentService.bringRentById(endRentWithExtraPaymentModel.getEndRentRequest().getRentId());

        this.rentService.checkIfRentAlreadyEnded(rental);

        double totalPrice = this.rentService.calculateExtraDaysPrice(rental.getRentalId(), LocalDate.now());

        checkIfExtraPaymentNeeded(totalPrice);

        rental.setRentalReturnDate(LocalDate.now());

        Invoice invoice = this.invoiceService.addExtraInvoice(rental.getRentalId(), totalPrice).getData();

        setCreatePaymentRequestFields(endRentWithExtraPaymentModel.getCreatePaymentRequest(), rental, invoice);

        this.paymentService.add(endRentWithExtraPaymentModel.getCreatePaymentRequest());

        this.rentService.endRent(endRentWithExtraPaymentModel.getEndRentRequest());

        checkIfUserWantsToSaveCreditCard(endRentWithExtraPaymentModel.getCreditCardSaveOption(),
                endRentWithExtraPaymentModel.getCreatePaymentRequest());

        return new SuccessResult(BusinessMessages.RENT_ENDED_WITH_PAYMENT + totalPrice);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result updateRent(UpdateRentModel updateRentModel) throws BusinessException {

        this.rentService.checkIfRentIdExists(updateRentModel.getUpdateRentRequest().getRentId());

        Rental rental = this.rentService.bringRentById(updateRentModel.getUpdateRentRequest().getRentId());

        this.rentService.checkIfRentAlreadyEnded(rental);

        addOrderedServicesToRent(updateRentModel.getCreateOrderedServiceRequests(), rental.getRentalId());

        double totalPrice = this.rentService.calculateExtraDaysPrice(
                rental.getRentalId(), updateRentModel.getUpdateRentRequest().getRentReturnDate());

        this.rentService.update(updateRentModel.getUpdateRentRequest());

        Invoice invoice = this.invoiceService.addExtraInvoice(rental.getRentalId(), totalPrice).getData();

        setCreatePaymentRequestFields(updateRentModel.getCreatePaymentRequest(), rental, invoice);

        this.paymentService.add(updateRentModel.getCreatePaymentRequest());

        checkIfUserWantsToSaveCreditCard(updateRentModel.getCreditCardSaveOption(), updateRentModel.getCreatePaymentRequest());

        return new SuccessResult(BusinessMessages.RENT_UPDATED + totalPrice);
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
    public void checkIfUserWantsToSaveCreditCard(String enumSaveCreditCard, CreatePaymentRequest createPaymentRequest) throws BusinessException {

        if (enumSaveCreditCard.equalsIgnoreCase("yes")) {

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
    public void setCreatePaymentRequestFields(CreatePaymentRequest createPaymentRequest, Rental rental, Invoice invoice) {

        createPaymentRequest.setRentId(rental.getRentalId());
        createPaymentRequest.setInvoiceId(invoice.getInvoiceId());
        createPaymentRequest.setCustomerUserId(rental.getCustomer().getUserId());
        createPaymentRequest.setPaymentAmount(invoice.getTotalPrice());
    }


}
