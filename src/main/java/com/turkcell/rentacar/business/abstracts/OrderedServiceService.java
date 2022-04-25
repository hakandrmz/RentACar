package com.turkcell.rentacar.business.abstracts;

import java.util.List;

import com.turkcell.rentacar.business.dtos.orderedService.GetOrderedServiceDto;
import com.turkcell.rentacar.business.dtos.orderedService.OrderedServiceListDto;
import com.turkcell.rentacar.business.requests.orderedService.CreateOrderedServiceRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

public interface OrderedServiceService {

    DataResult<List<OrderedServiceListDto>> getAll();

    Result add(CreateOrderedServiceRequest createOrderedServiceRequest);

    DataResult<GetOrderedServiceDto> getByOrderedServiceId(Integer id) throws BusinessException;

    DataResult<List<OrderedServiceListDto>> getByRentId(Integer id) throws BusinessException;

    void checkIfOrderedServiceIdExists(Integer id) throws BusinessException;

    double calculateOrderedServicePrice(int rentId) throws BusinessException;
}
