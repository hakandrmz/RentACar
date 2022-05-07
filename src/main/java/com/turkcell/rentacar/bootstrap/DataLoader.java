package com.turkcell.rentacar.bootstrap;

import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.dataaccess.abstracts.*;
import com.turkcell.rentacar.entities.concretes.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final CarDao carDao;
    private final ColorDao colorDao;
    private final CityDao cityDao;
    private final BrandDao brandDao;
    private final AdditionalServiceDao additionalServiceDao;
    private final IndividualCustomerDao individualCustomerDao;
    private final CorporateCustomerDao corporateCustomerDao;
    private final DamageDao damageDao;

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
            cityDao.save(City.builder().cityName("Istanbul").build());
            cityDao.save(City.builder().cityName("Adana").build());

            additionalServiceDao.save(AdditionalService.builder().additionalServiceName("Bebek arabası").dailyPrice(300).build());
            additionalServiceDao.save(AdditionalService.builder().additionalServiceName("Ek bagaj").dailyPrice(200).build());
            additionalServiceDao.save(AdditionalService.builder().additionalServiceName("Kaza sigortası").dailyPrice(100).build());

            carDao.save(Car
                    .builder()
                    .kilometer(780.0)
                    .description("2020 Model")
                    .modelYear(2020).dailyPrice(800)
                    .color(Color.builder().id(1).build())
                    .brand(Brand.builder().id(3).build())
                    .build());
            carDao.save(Car
                    .builder()
                    .kilometer(1500.0)
                    .description("2019 Model")
                    .modelYear(2019).dailyPrice(700)
                    .color(Color.builder().id(2).build())
                    .brand(Brand.builder().id(3).build())
                    .build());
            carDao.save(Car
                    .builder()
                    .kilometer(5000.0)
                    .description("2012 Model")
                    .modelYear(2012).dailyPrice(600)
                    .color(Color.builder().id(3).build())
                    .brand(Brand.builder().id(1).build())
                    .build());

            individualCustomerDao.save(IndividualCustomer.builder().nationalIdentity("12345678912").firstName("Hakan").lastName("Durmaz").build());
            individualCustomerDao.save(IndividualCustomer.builder().nationalIdentity("12345678914").firstName("Furkan").lastName("Yıldırım").build());
            individualCustomerDao.save(IndividualCustomer.builder().nationalIdentity("12345678915").firstName("Enes").lastName("Çırak").build());

            corporateCustomerDao.save(CorporateCustomer.builder().companyName("Turkcell").taxNumber("123123123").build());
            corporateCustomerDao.save(CorporateCustomer.builder().companyName("Atmosware").taxNumber("123123124").build());
            corporateCustomerDao.save(CorporateCustomer.builder().companyName("Eregli Demir Çelik").taxNumber("123123125").build());

            damageDao.save(Damage.builder().description("First damage of car id:1").car(Car.builder().id(1).build()).build());
            damageDao.save(Damage.builder().description("Second damage of car id:1").car(Car.builder().id(1).build()).build());
            damageDao.save(Damage.builder().description("First damage of car id:2").car(Car.builder().id(2).build()).build());
            damageDao.save(Damage.builder().description("Second damage of car id:2").car(Car.builder().id(2).build()).build());


        }
    }
}
