package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.CreditCardService;
import com.turkcell.rentacar.business.abstracts.CustomerService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.dtos.creditCard.CreditCardListDto;
import com.turkcell.rentacar.business.dtos.creditCard.GetCreditCardDto;
import com.turkcell.rentacar.business.requests.creditCard.CreateCreditCardRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.CreditCardDao;
import com.turkcell.rentacar.entities.concretes.CreditCard;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreditCardManager implements CreditCardService {
    private final CreditCardDao creditCardDao;
    private final ModelMapperService modelMapperService;
    private final CustomerService customerService;

    @Override
    public Result add(CreateCreditCardRequest createCreditCardRequest) {

        CreditCard creditCard = this.modelMapperService.forRequest().map(createCreditCardRequest, CreditCard.class);

        creditCard.setCreditCardId(0);

        this.creditCardDao.save(creditCard);

        return new SuccessResult(BusinessMessages.CREDIT_CARD_ADDED);
    }

    @Override
    public DataResult<List<CreditCardListDto>> getAll() {

        var result = this.creditCardDao.findAll();

        var response = result.stream().map(creditCard -> this.modelMapperService
                .forDto().map(creditCard, CreditCardListDto.class)).collect(Collectors.toList());

        return new SuccessDataResult<>(response, BusinessMessages.CREDIT_CARDS_LISTED);
    }

    @Override
    public DataResult<GetCreditCardDto> getById(Integer id) throws BusinessException {

        checkIfCreditCardIdExists(id);

        var creditCard = this.creditCardDao.getById(id);

        GetCreditCardDto getCreditCardDto = this.modelMapperService.forDto().map(creditCard, GetCreditCardDto.class);

        return new SuccessDataResult<>(getCreditCardDto, BusinessMessages.CREDIT_CARD_FOUND_BY_ID);
    }

    @Override
    public DataResult<List<CreditCardListDto>> getByCustomerUserId(int customerUserId) throws BusinessException {

        this.customerService.checkIfCustomerIdExists(customerUserId);

        var result = this.creditCardDao.findByCustomerUserId(customerUserId);

        List<CreditCardListDto> response = result.stream().map(creditCard -> this.modelMapperService.forDto()
                .map(creditCard, CreditCardListDto.class)).collect(Collectors.toList());

        return new SuccessDataResult<>(response, BusinessMessages.CREDIT_CARDS_LISTED_BY_CUSTOMER_ID);
    }

    @Override
    public void checkIfCreditCardIdExists(int creditCardId) throws BusinessException {

        if (!this.creditCardDao.existsById(creditCardId)) {

            throw new BusinessException(BusinessMessages.CREDIT_CARD_NOT_FOUND);
        }
    }
}
