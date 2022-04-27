package com.turkcell.rentacar.business.abstracts;

import java.util.List;

import com.turkcell.rentacar.business.dtos.payment.GetPaymentDto;
import com.turkcell.rentacar.business.dtos.payment.PaymentListDto;
import com.turkcell.rentacar.business.requests.payment.CreatePaymentRequest;
import com.turkcell.rentacar.business.requests.pos.CreatePosRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

public interface PaymentService {

    Result add(CreatePaymentRequest createPaymentRequest);

    DataResult<List<PaymentListDto>> getAll();

    DataResult<GetPaymentDto> getByPaymentId(int paymentId);

    DataResult<List<PaymentListDto>> getByCustomerUserId(int userId);

    DataResult<GetPaymentDto> getByInvoiceId(int invoiceId);

    DataResult<List<PaymentListDto>> getByRentId(int rentId);

    void makePayment(CreatePosRequest createPosRequest, double paymentAmount);

    void checkIfPaymentIdExists(int paymentId);

}
