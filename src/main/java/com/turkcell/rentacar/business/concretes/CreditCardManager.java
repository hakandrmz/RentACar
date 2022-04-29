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

        return new SuccessResult(BusinessMessages.SUCCESSFULLY_ADDED);
    }

    @Override
    public DataResult<List<CreditCardListDto>> getAll() {

        List<CreditCard> result = this.creditCardDao.findAll();

        List<CreditCardListDto> response;
        response = result.stream().map(creditCard -> this.modelMapperService
                .forDto().map(creditCard, CreditCardListDto.class)).collect(Collectors.toList());

        return new SuccessDataResult<>(response, BusinessMessages.SUCCESSFULLY_LISTED);
    }

    @Override
    public DataResult<GetCreditCardDto> getById(Integer id) throws BusinessException {

        checkIfCreditCardIdExists(id);

        CreditCard creditCard = this.creditCardDao.getById(id);

        GetCreditCardDto getCreditCardDto = this.modelMapperService.forDto().map(creditCard, GetCreditCardDto.class);

        return new SuccessDataResult<>(getCreditCardDto, BusinessMessages.SUCCESSFULLY_FOUND);
    }

    @Override
    public DataResult<List<CreditCardListDto>> getByCustomerUserId(int customerUserId) throws BusinessException {

        this.customerService.checkIfCustomerIdExists(customerUserId);

        List<CreditCard> result = this.creditCardDao.findByCustomerUserId(customerUserId);

        List<CreditCardListDto> response = result.stream().map(creditCard -> this.modelMapperService.forDto()
                .map(creditCard, CreditCardListDto.class)).collect(Collectors.toList());

        return new SuccessDataResult<>(response, BusinessMessages.SUCCESSFULLY_LISTED);
    }

    @Override
    public void checkIfCreditCardIdExists(int creditCardId) throws BusinessException {

        creditCardDao.findById(creditCardId)
                .orElseThrow(()
                        -> new BusinessException(BusinessMessages.NOT_FOUND));

    }
}
