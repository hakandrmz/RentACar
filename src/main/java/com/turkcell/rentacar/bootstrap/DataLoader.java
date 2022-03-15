package com.turkcell.rentacar.bootstrap;

import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.dataAccess.abstracts.*;
import com.turkcell.rentacar.entities.concretes.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final CarDao carDao;
    private final ColorDao colorDao;
    private final CityDao cityDao;
    private final BrandDao brandDao;
    private final AdditionalServiceDao additionalServiceDao;
    private final OrderedAdditionalServiceDao orderedAdditionalServiceDao;

    @Override
    public void run(String... args) throws BusinessException {
        if (brandDao.findAll().isEmpty()) {

            brandDao.save(Brand.builder().brandName("BMW").build());
            brandDao.save(Brand.builder().brandName("Mercedes").build());
            brandDao.save(Brand.builder().brandName("Audi").build());
            brandDao.save(Brand.builder().brandName("Fiat").build());

            colorDao.save(Color.builder().colorName("Mavi").build());
            colorDao.save(Color.builder().colorName("Sarı").build());
            colorDao.save(Color.builder().colorName("Kırmızı").build());
            colorDao.save(Color.builder().colorName("Siyah").build());

            cityDao.save(City.builder().cityName("Bursa").build());
            cityDao.save(City.builder().cityName("Ankara").build());
            cityDao.save(City.builder().cityName("İstanbul").build());
            cityDao.save(City.builder().cityName("Adana").build());

            additionalServiceDao.save(AdditionalService.builder().additionalServiceName("Bebek arabası").dailyPrice(300).build());
            additionalServiceDao.save(AdditionalService.builder().additionalServiceName("Ek bagaj").dailyPrice(200).build());
            additionalServiceDao.save(AdditionalService.builder().additionalServiceName("Kaza sigortası").dailyPrice(200).build());

            carDao.save(Car
                    .builder()
                    .carDescription("2020 Model")
                    .modelYear(2020).dailyPrice(800)
                    .color(Color.builder().colorId(1).build())
                    .brand(Brand.builder().brandId(3).build())
                    .build());
            carDao.save(Car
                    .builder()
                    .carDescription("2019 Model")
                    .modelYear(2019).dailyPrice(700)
                    .color(Color.builder().colorId(2).build())
                    .brand(Brand.builder().brandId(3).build())
                    .build());
            carDao.save(Car
                    .builder()
                    .carDescription("2012 Model")
                    .modelYear(2012).dailyPrice(600)
                    .color(Color.builder().colorId(3).build())
                    .brand(Brand.builder().brandId(1).build())
                    .build());

            UUID uuid = UUID.randomUUID();
            orderedAdditionalServiceDao.save(OrderedAdditionalService.builder().orderedAdditionalServiceId(uuid).additionalService(AdditionalService.builder().additionalServiceId(1).build()).build());
            orderedAdditionalServiceDao.save(OrderedAdditionalService.builder().orderedAdditionalServiceId(uuid).additionalService(AdditionalService.builder().additionalServiceId(2).build()).build());
            orderedAdditionalServiceDao.save(OrderedAdditionalService.builder().orderedAdditionalServiceId(uuid).additionalService(AdditionalService.builder().additionalServiceId(3).build()).build());

        }
    }
}
