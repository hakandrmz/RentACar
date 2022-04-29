package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.*;
import com.turkcell.rentacar.business.adapters.posAdapters.INGBankPosAdapter;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.dtos.payment.GetPaymentDto;
import com.turkcell.rentacar.business.dtos.payment.PaymentListDto;
import com.turkcell.rentacar.business.requests.payment.CreatePaymentRequest;
import com.turkcell.rentacar.business.requests.pos.CreatePosRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.PaymentDao;
import com.turkcell.rentacar.entities.concretes.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentManager implements PaymentService {

    private final PaymentDao paymentDao;
    private final ModelMapperService modelMapperService;
    private final RentService rentService;
    private final CustomerService customerService;
    private final InvoiceService invoiceService;

    @Override
    public Result add(CreatePaymentRequest createPaymentRequest) throws BusinessException {

        makePayment(createPaymentRequest.getCreatePosRequest(), createPaymentRequest.getPaymentAmount());

        Payment payment = this.modelMapperService.forRequest().map(createPaymentRequest, Payment.class);

        this.paymentDao.save(payment);

        return new SuccessResult(BusinessMessages.SUCCESSFULLY_ADDED);
    }

    @Override
    public DataResult<List<PaymentListDto>> getAll() {

        List<Payment> result = this.paymentDao.findAll();

        List<PaymentListDto> response = result.stream().map(payment -> this.modelMapperService
                .forDto().map(payment, PaymentListDto.class)).collect(Collectors.toList());

        return new SuccessDataResult<>(response, BusinessMessages.SUCCESSFULLY_LISTED);
    }

    @Override
    public DataResult<GetPaymentDto> getByPaymentId(int paymentId) throws BusinessException {

        checkIfPaymentIdExists(paymentId);

        Payment result = this.paymentDao.getById(paymentId);

        GetPaymentDto response = this.modelMapperService.forDto().map(result, GetPaymentDto.class);

        return new SuccessDataResult<>(response, BusinessMessages.SUCCESSFULLY_FOUND);
    }

    @Override
    public DataResult<List<PaymentListDto>> getByCustomerUserId(int userId) throws BusinessException {

        this.customerService.checkIfCustomerIdExists(userId);

        List<Payment> result = this.paymentDao.findByCustomerUserId(userId);

        List<PaymentListDto> response = result.stream().map(payment -> this.modelMapperService
                .forDto().map(payment, PaymentListDto.class)).collect(Collectors.toList());

        return new SuccessDataResult<>(response, BusinessMessages.SUCCESSFULLY_LISTED);
    }

    @Override
    public DataResult<GetPaymentDto> getByInvoiceId(int invoiceId) throws BusinessException {

        this.invoiceService.checkIfInvoiceIdExists(invoiceId);

        Payment payment = this.paymentDao.findByInvoiceInvoiceId(invoiceId);

        GetPaymentDto response = this.modelMapperService.forDto().map(payment, GetPaymentDto.class);

        return new SuccessDataResult<GetPaymentDto>(response, BusinessMessages.SUCCESSFULLY_FOUND);
    }

    @Override
    public DataResult<List<PaymentListDto>> getByRentId(int rentId) throws BusinessException {

        this.rentService.checkIfRentIdExists(rentId);

        List<Payment> result = this.paymentDao.findByRentRentId(rentId);

        List<PaymentListDto> response = result.stream().map(payment -> this.modelMapperService
                .forDto().map(payment, PaymentListDto.class)).collect(Collectors.toList());

        return new SuccessDataResult<>(response, BusinessMessages.SUCCESSFULLY_LISTED);
    }

    @Override
    public void makePayment(CreatePosRequest createPosRequest, double paymentAmount) throws BusinessException {

        PosService posService = new INGBankPosAdapter();

        if (!posService.pay(createPosRequest, paymentAmount)) {

            throw new BusinessException(BusinessMessages.PAYMENT_UNSUCCESSFULLY);
        }
    }

    @Override
    public void checkIfPaymentIdExists(int paymentId) throws BusinessException {
        paymentDao.findById(paymentId).orElseThrow(() -> new BusinessException("Payment not found with id: " + paymentId));
    }
}




