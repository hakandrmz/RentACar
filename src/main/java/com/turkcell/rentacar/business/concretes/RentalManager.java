package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.api.controller.models.CreateRentalAndOrderedAdditionalModel;
import com.turkcell.rentacar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentacar.business.abstracts.CarService;
import com.turkcell.rentacar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentacar.business.abstracts.RentalService;
import com.turkcell.rentacar.business.dtos.carmaintenance.CarMaintenanceListDto;
import com.turkcell.rentacar.business.dtos.rental.RentalDtoById;
import com.turkcell.rentacar.business.dtos.rental.RentalListDto;
import com.turkcell.rentacar.business.requests.orderedadditionalservice.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentacar.business.requests.rental.CreateRentalRequest;
import com.turkcell.rentacar.business.requests.rental.UpdateRentalRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.RentalDao;
import com.turkcell.rentacar.entities.concretes.CarMaintenance;
import com.turkcell.rentacar.entities.concretes.OrderedAdditionalService;
import com.turkcell.rentacar.entities.concretes.Rental;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RentalManager implements RentalService {

    private final RentalDao rentalDao;
    private final ModelMapperService modelMapperService;
    private final CarMaintenanceService carMaintenanceService;
    private final CarService carService;
    private final OrderedAdditionalServiceService orderedAdditionalServiceService;


    public RentalManager(RentalDao rentalDao,
                         ModelMapperService modelMapperService,
                         @Lazy CarMaintenanceService carMaintenanceService,
                         @Lazy CarService carService,
                         @Lazy OrderedAdditionalServiceService orderedAdditionalServiceService) {

        this.rentalDao = rentalDao;
        this.modelMapperService = modelMapperService;
        this.carMaintenanceService = carMaintenanceService;
        this.carService = carService;
        this.orderedAdditionalServiceService = orderedAdditionalServiceService;
    }

    @Override
    public DataResult<List<RentalListDto>> getAll() {
        List<Rental> result = this.rentalDao.findAll();
        List<RentalListDto> response = result.stream().map(rental -> this.modelMapperService.forDto().map(rental, RentalListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<>(response, "Rents listed");
    }


    @Override
    public Result add(CreateRentalAndOrderedAdditionalModel createRentalAndOrderedAdditionalModel) {

        CreateRentalRequest createRentalRequest = createRentalAndOrderedAdditionalModel.getCreateRentalRequest();

        checkIfCarIsAvailable(createRentalRequest.getCarId(), createRentalRequest.getStartDate());
        checkIfStartDateBeforeThanEndDate(createRentalRequest.getStartDate(), createRentalRequest.getEndDate());

        UUID savedOrderedAdditionalModelUUID = saveOrderedAdditionalServiceAndReturnUUID(createRentalAndOrderedAdditionalModel.getCreateOrderedAdditionalServiceRequest());

        Rental rental = this.modelMapperService.forDto().map(createRentalRequest, Rental.class);

        rental.setOrderedAdditionalServices(OrderedAdditionalService.builder().orderedAdditionalServiceId(savedOrderedAdditionalModelUUID).build());

        this.rentalDao.save(rental);

        return new SuccessResult("Rent is added");
    }


    @Override
    public DataResult<RentalDtoById> getById(int id) {
        checkIfRentalExists(id);

        Rental rental = this.rentalDao.getById(id);

        RentalDtoById rentalDtoById = this.modelMapperService.forDto().map(rental, RentalDtoById.class);

        return new SuccessDataResult<>(rentalDtoById, "Rent listed");
    }

    @Override
    public Result update(UpdateRentalRequest updateRentalRequest) {

        checkIfRentalExists(updateRentalRequest.getRentalId());
        checkIfCarIsAvailable(updateRentalRequest.getCarCarId(), updateRentalRequest.getStartDate());
        checkIfStartDateBeforeThanEndDate(updateRentalRequest.getStartDate(), updateRentalRequest.getEndDate());

        Rental rental = this.modelMapperService.forRequest().map(updateRentalRequest, Rental.class);

        this.rentalDao.save(rental);

        return new SuccessResult("Rent updated");
    }

    private void checkIfCarIsAvailable(int carId, LocalDate startDate) {
        checkIfCarIsRented(carId, startDate);
        checkIfCarIsInMaintenance(carId, startDate);
    }

    private void checkIfCarIsRented(int carId, LocalDate startDate) {

        this.carService.checkIfCarExists(carId);

        DataResult<List<RentalListDto>> result = getAllByCarCarId(carId);

        List<Rental> response = result.getData().stream()
                .map(rental -> this.modelMapperService.forDto()
                        .map(rental, Rental.class))
                .collect(Collectors.toList());


        for (Rental rental : response) {
            if (!rental.getEndDate().isBefore(startDate)) {
                throw new BusinessException("Car is already rented!");
            }
        }
    }

    private void checkIfCarIsInMaintenance(int carId, LocalDate startDate) {

        DataResult<List<CarMaintenanceListDto>> result = this.carMaintenanceService.getByCarId(carId);

        List<CarMaintenance> response = result.getData().stream().map(carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, CarMaintenance.class)).collect(Collectors.toList());

        for (CarMaintenance carMaintenance : response) {

            if (carMaintenance.getReturnDate() == null || startDate.isBefore(carMaintenance.getReturnDate())) {
                throw new BusinessException("Car is in maintenance!");
            }
        }
    }

    @Transactional
    public UUID saveOrderedAdditionalServiceAndReturnUUID(CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest) {

        DataResult dataResult = orderedAdditionalServiceService.saveDbFromModelAndGetUUID(createOrderedAdditionalServiceRequest);
        return (UUID) dataResult.getData();
    }

    @Override
    public DataResult<List<RentalListDto>> getAllByCarCarId(int id) {

        this.carService.checkIfCarExists(id);

        List<Rental> result = this.rentalDao.getAllByCarCarId(id);

        List<RentalListDto> response = result.stream().map(rent -> this.modelMapperService.forDto().map(rent, RentalListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(response, "Car's rent info listed");
    }

    @Override
    public Result delete(int rentalId) {
        checkIfRentalExists(rentalId);
        this.rentalDao.deleteById(rentalId);
        return new SuccessResult("Rental is deleted.");
    }

    private void checkIfRentalExists(int id) {
        if (!rentalDao.existsById(id)) {
            throw new BusinessException("Rental does not exist by id:" + id);
        }
    }

    private boolean checkIfRentalIsReturnDifferentCity(Rental rental) {
        return rental.getRentedCity() != rental.getDeliveredCity();
    }

    private int calculateAdditionalPriceForReturnLocation(Rental rental) {
        int additionalPrice = 0;
        if (checkIfRentalIsReturnDifferentCity(rental)) {
            additionalPrice = 750;
        }
        return additionalPrice;
    }

    private void checkIfStartDateBeforeThanEndDate(LocalDate startDate, LocalDate endDate) {
        if (endDate.isBefore(startDate)) {
            throw new BusinessException("Invalid date format ");
        }
    }


}
