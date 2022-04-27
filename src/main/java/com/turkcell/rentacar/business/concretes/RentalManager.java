package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentacar.business.abstracts.CarService;
import com.turkcell.rentacar.business.abstracts.OrderedServiceService;
import com.turkcell.rentacar.business.abstracts.RentService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.dtos.orderedService.OrderedServiceListDto;
import com.turkcell.rentacar.business.dtos.rent.GetRentDto;
import com.turkcell.rentacar.business.dtos.rent.RentListDto;
import com.turkcell.rentacar.business.requests.rent.CreateRentRequest;
import com.turkcell.rentacar.business.requests.rent.EndRentRequest;
import com.turkcell.rentacar.business.requests.rent.UpdateRentRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.exceptions.rent.*;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.RentDao;
import com.turkcell.rentacar.entities.concretes.Car;
import com.turkcell.rentacar.entities.concretes.Rental;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalManager implements RentService {

    private final RentDao rentDao;
    private final ModelMapperService modelMapperService;
    private final CarMaintenanceService carMaintenanceService;
    private final CarService carService;
    private final OrderedServiceService orderedServiceService;


    @Autowired
    public RentalManager(RentDao rentDao, ModelMapperService modelMapperService,
                         CarMaintenanceService carMaintenanceService, CarService carService, @Lazy OrderedServiceService orderedServiceService) {

        this.rentDao = rentDao;
        this.modelMapperService = modelMapperService;
        this.carMaintenanceService = carMaintenanceService;
        this.carService = carService;
        this.orderedServiceService = orderedServiceService;
    }

    @Override
    public DataResult<List<RentListDto>> getAll() {

        List<Rental> rentals = this.rentDao.findAll();

        List<RentListDto> response = rentals.stream().map(rent -> this.modelMapperService
                .forDto().map(rent, RentListDto.class)).collect(Collectors.toList());

        return new SuccessDataResult<List<RentListDto>>(response, BusinessMessages.RENTS_LISTED);
    }

    @Override
    public DataResult<Rental> add(CreateRentRequest createRentRequest) throws BusinessException {

        this.carMaintenanceService.checkIfCarIsInMaintenance(createRentRequest.getCarId());

        checkIfCarIsRented(createRentRequest.getCarId());
        checkIfDatesAreCorrect(createRentRequest.getRentStartDate(), createRentRequest.getRentReturnDate(), 0);

        Rental rental = this.modelMapperService.forRequest().map(createRentRequest, Rental.class);

        rental.setRentalId(0);

        rental.setStartKilometer(carService.getByCarId(createRentRequest.getCarId()).getData().getKilometer());

        Rental savedRental = this.rentDao.save(rental);

        this.carService.updateRentStatus(rental.getCar().getId(), true);

        return new SuccessDataResult<Rental>(savedRental, BusinessMessages.RENT_ADDED);
    }


    @Override
    public DataResult<GetRentDto> getByRentId(int id) throws BusinessException {

        checkIfRentIdExists(id);

        Rental rental = this.rentDao.getById(id);

        GetRentDto response = this.modelMapperService.forDto().map(rental, GetRentDto.class);

        return new SuccessDataResult<GetRentDto>(response, BusinessMessages.RENT_FOUND_BY_ID);
    }

    @Override
    public Result update(UpdateRentRequest updateRentRequest) throws BusinessException {

        checkIfRentIdExists(updateRentRequest.getRentId());

        Rental rental = this.rentDao.getById(updateRentRequest.getRentId());

        checkIfDatesAreCorrect(rental.getRentalReturnDate(), updateRentRequest.getRentReturnDate(), 1);

        rental.setRentalReturnDate(updateRentRequest.getRentReturnDate());

        this.rentDao.save(rental);

        return new SuccessResult(BusinessMessages.RENT_UPDATED);
    }

    @Override
    public Result delete(int rentalId) throws BusinessException {

        checkIfRentIdExists(rentalId);

        this.rentDao.deleteById(rentalId);

        return new SuccessResult(BusinessMessages.RENT_DELETED);
    }


    @Override
    public DataResult<List<RentListDto>> getByCarId(int id) throws BusinessException {

        this.carService.checkIfCarIdExists(id);

        List<Rental> rentals = this.rentDao.getAllByCarId(id);

        List<RentListDto> response = rentals.stream().map(rent -> this.modelMapperService
                .forDto().map(rent, RentListDto.class)).collect(Collectors.toList());

        return new SuccessDataResult<List<RentListDto>>(response, BusinessMessages.RENTS_LISTED_BY_CAR_ID);
    }


    @Override
    public Result endRent(EndRentRequest endRentRequest) throws BusinessException {

        checkIfRentIdExists(endRentRequest.getRentId());

        Rental rental = this.rentDao.getById(endRentRequest.getRentId());

        checkIfRentAlreadyEnded(rental);
        checkIfReturnDateDelayed(rental);
        checkIfEndKilometerIsCorrect(rental.getStartKilometer(), endRentRequest.getEndKilometer());

        rental.setRentalReturnDate(LocalDate.now());
        rental.setEndKilometer(endRentRequest.getEndKilometer());

        this.rentDao.save(rental);

        this.carService.setCarKilometer(rental.getCar().getId(), endRentRequest.getEndKilometer());
        this.carService.updateRentStatus(rental.getCar().getId(), false);

        return new SuccessResult(BusinessMessages.RENT_ENDED);
    }

    @Override
    public void checkIfCarIsRented(int id) throws BusinessException {

        Car car = this.carService.getCarByCarId(id);

        if (car.isRentStatus()) {

            throw new CarIsCurrentlyRentedException(BusinessMessages.CAR_IS_CURRENTLY_RENTED);
        }
    }

    @Override
    public void checkIfRentAlreadyEnded(Rental rental) throws BusinessException {

        Car car = this.carService.getCarByCarId(rental.getCar().getId());

        if (!car.isRentStatus() || rental.getEndKilometer() != null) {

            throw new CarIsCurrentlyRentedException(BusinessMessages.RENT_ALREADY_ENDED);
        }
    }

    @Override
    public void checkIfRentIdExists(Integer id) throws BusinessException {

        if (!this.rentDao.existsById(id)) {

            throw new RentNotFoundException(BusinessMessages.RENT_NOT_FOUND);
        }
    }

    @Override
    public double calculateRentPrice(int rentId) {

        double differentCityPrice = 0;

        if (!(this.rentDao.getById(rentId).getRentalCity().equals(this.rentDao.getById(rentId).getReturnCity()))) {

            differentCityPrice = 750;
        }

        long daysBetween = (ChronoUnit.DAYS.between(
                this.rentDao.getById(rentId).getRentStartDate(), this.rentDao.getById(rentId).getRentalReturnDate()) + 1);

        Car car = this.carService.getCarByCarId(this.rentDao.getById(rentId).getCar().getId());

        double dailyPrice = car.getDailyPrice();

        double totalRentPrice = (daysBetween * dailyPrice) + differentCityPrice;

        return totalRentPrice;
    }

    @Override
    public Rental bringRentById(int rentId) throws BusinessException {

        checkIfRentIdExists(rentId);

        return this.rentDao.getById(rentId);
    }

    @Override
    public void checkIfReturnDateDelayed(Rental rental) throws BusinessException {

        if (LocalDate.now().isAfter(rental.getRentalReturnDate())) {

            double extraPrice = calculateExtraDaysPrice(rental.getRentalId(), LocalDate.now());

            throw new RentReturnDateDelayedException(BusinessMessages.NEED_EXTRA_PAYMENT + extraPrice);
        }
    }

    @Override
    public double calculateExtraDaysPrice(int rentId, LocalDate date) throws BusinessException {

        Rental rental = this.rentDao.getById(rentId);

        long daysBetween = (ChronoUnit.DAYS.between(rental.getRentalReturnDate(), date));

        Car car = this.carService.getCarByCarId(rental.getCar().getId());

        double dailyPrice = car.getDailyPrice();

        double extraDaysPrice = daysBetween * dailyPrice;

        List<OrderedServiceListDto> orderedServices = this.orderedServiceService.getByRentId(rentId).getData();

        double extraOrderedServicesPrice = 0;

        for (OrderedServiceListDto orderedServiceListDto : orderedServices) {

            extraOrderedServicesPrice += orderedServiceListDto.getAdditionalServiceDailyPrice() * orderedServiceListDto.getOrderedServiceAmount();
        }

        extraOrderedServicesPrice *= daysBetween;

        double extraPrice = extraDaysPrice + extraOrderedServicesPrice;

        return extraPrice;
    }

    @Override
    public void checkIfDatesAreCorrect(LocalDate rentDate, LocalDate returnDate, int key) throws BusinessException {

        if (key == 0 && (rentDate.isBefore(LocalDate.now()) || returnDate.isBefore(LocalDate.now())
                || returnDate.isBefore(rentDate))) {

            throw new RentDatesNotCorrectException(BusinessMessages.RENT_DATES_NOT_CORRECT);
        }

        if (key == 1 && (returnDate.isBefore(rentDate))) {

            throw new RentReturnDateNotCorrectException(BusinessMessages.RETURN_DATE_NOT_CORRECT);
        }
    }

    @Override
    public void checkIfEndKilometerIsCorrect(double startKilometer, double endKilometer) throws BusinessException {

        if (startKilometer > endKilometer) {

            throw new RentEndKilometerNotCorrectException(BusinessMessages.END_KILOMETER_NOT_CORRECT);
        }
    }
}
