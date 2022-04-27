package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.*;
import com.turkcell.rentacar.business.adapters.posAdapters.IsBankPosAdapter;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.dtos.payment.GetPaymentDto;
import com.turkcell.rentacar.business.dtos.payment.PaymentListDto;
import com.turkcell.rentacar.business.requests.payment.CreatePaymentRequest;
import com.turkcell.rentacar.business.requests.pos.CreatePosRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.exceptions.payment.PaymentNotFoundException;
import com.turkcell.rentacar.core.exceptions.payment.PaymentUnsuccessfullException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.PaymentDao;
import com.turkcell.rentacar.entities.concretes.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentManager implements PaymentService {

    private final PaymentDao paymentDao;
    private final ModelMapperService modelMapperService;
    private final RentService rentService;
    private final CustomerService customerService;
    private final InvoiceService invoiceService;

    @Autowired
    public PaymentManager(PaymentDao paymentDao, ModelMapperService modelMapperService, RentService rentService,
                          CustomerService customerService, InvoiceService invoiceService) {

        this.paymentDao = paymentDao;
        this.modelMapperService = modelMapperService;
        this.rentService = rentService;
        this.customerService = customerService;
        this.invoiceService = invoiceService;
    }

    @Override
    public Result add(CreatePaymentRequest createPaymentRequest) {

        makePayment(createPaymentRequest.getCreatePosRequest(), createPaymentRequest.getPaymentAmount());

        Payment payment = this.modelMapperService.forRequest().map(createPaymentRequest, Payment.class);

        this.paymentDao.save(payment);

        return new SuccessResult(BusinessMessages.PAYMENT_SUCCESSFULL);
    }

    @Override
    public DataResult<List<PaymentListDto>> getAll() {

        List<Payment> result = this.paymentDao.findAll();

        List<PaymentListDto> response = result.stream().map(payment -> this.modelMapperService
                .forDto().map(payment, PaymentListDto.class)).collect(Collectors.toList());

        return new SuccessDataResult<List<PaymentListDto>>(response, BusinessMessages.PAYMENTS_LISTED);
    }

    @Override
    public DataResult<GetPaymentDto> getByPaymentId(int paymentId) {

        checkIfPaymentIdExists(paymentId);

        Payment result = this.paymentDao.getById(paymentId);

        GetPaymentDto response = this.modelMapperService.forDto().map(result, GetPaymentDto.class);

        return new SuccessDataResult<GetPaymentDto>(response, BusinessMessages.PAYMENT_FOUND_BY_ID);
    }

    @Override
    public DataResult<List<PaymentListDto>> getByCustomerUserId(int userId) {

        this.customerService.checkIfCustomerIdExists(userId);

        List<Payment> result = this.paymentDao.findByCustomerUserId(userId);

        List<PaymentListDto> response = result.stream().map(payment -> this.modelMapperService
                .forDto().map(payment, PaymentListDto.class)).collect(Collectors.toList());

        return new SuccessDataResult<List<PaymentListDto>>(response, BusinessMessages.PAYMENTS_LISTED_BY_CUSTOMER_ID);
    }

    @Override
    public DataResult<GetPaymentDto> getByInvoiceId(int invoiceId) {

        this.invoiceService.checkIfInvoiceIdExists(invoiceId);

        Payment payment = this.paymentDao.findByInvoiceInvoiceId(invoiceId);

        GetPaymentDto response = this.modelMapperService.forDto().map(payment, GetPaymentDto.class);

        return new SuccessDataResult<GetPaymentDto>(response, BusinessMessages.PAYMENT_FOUND_BY_INVOICE_ID);
    }

    @Override
    public DataResult<List<PaymentListDto>> getByRentId(int rentId) {

        this.rentService.checkIfRentIdExists(rentId);

        List<Payment> result = this.paymentDao.findByRentalRentalId(rentId);

        List<PaymentListDto> response = result.stream().map(payment -> this.modelMapperService
                .forDto().map(payment, PaymentListDto.class)).collect(Collectors.toList());

        return new SuccessDataResult<List<PaymentListDto>>(response, BusinessMessages.PAYMENT_FOUND_BY_RENT_ID);
    }

    @Override
    public void makePayment(CreatePosRequest createPosRequest, double paymentAmount) {

        PosService posService = new IsBankPosAdapter();

        if (!posService.pay(createPosRequest, paymentAmount)) {

            throw new PaymentUnsuccessfullException(BusinessMessages.PAYMENT_UNSUCCESSFULL);
        }
    }

    @Override
    public void checkIfPaymentIdExists(int paymentId) {

        if (!this.paymentDao.existsById(paymentId)) {

            throw new PaymentNotFoundException(BusinessMessages.PAYMENT_NOT_FOUND);
        }
    }
}




