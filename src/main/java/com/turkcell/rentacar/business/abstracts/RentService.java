package com.turkcell.rentacar.business.abstracts;

import java.time.LocalDate;
import java.util.List;

import com.turkcell.rentacar.business.dtos.rent.GetRentDto;
import com.turkcell.rentacar.business.dtos.rent.RentListDto;
import com.turkcell.rentacar.business.requests.rent.CreateRentRequest;
import com.turkcell.rentacar.business.requests.rent.DeleteRentRequest;
import com.turkcell.rentacar.business.requests.rent.EndRentRequest;
import com.turkcell.rentacar.business.requests.rent.UpdateRentRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.entities.concretes.Rental;

public interface RentService {

    DataResult<List<RentListDto>> getAll();

    DataResult<Rental> add(CreateRentRequest createRentRequest);

    Result update(UpdateRentRequest updateRentRequest);

    Result delete(int id);

    DataResult<List<RentListDto>> getByCarId(int id);

    DataResult<GetRentDto> getByRentId(int id);

    Result endRent(EndRentRequest endRentRequest);

    void checkIfCarIsRented(int id);

    void checkIfRentIdExists(Integer id);

    double calculateRentPrice(int rentId);

    Rental bringRentById(int rentId);

    void checkIfReturnDateDelayed(Rental rental);

    double calculateExtraDaysPrice(int rentId, LocalDate date);

    void checkIfRentAlreadyEnded(Rental rental);

    void checkIfDatesAreCorrect(LocalDate rentDate, LocalDate returnDate, int key);

    void checkIfEndKilometerIsCorrect(double startKilometer, double endKilometer);

}
