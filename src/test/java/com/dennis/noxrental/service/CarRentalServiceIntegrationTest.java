package com.dennis.noxrental.service;


import com.dennis.noxrental.TestHelper;
import com.dennis.noxrental.entity.Car;
import com.dennis.noxrental.entity.Rental;
import com.dennis.noxrental.repository.CarRentalRepository;
import com.dennis.noxrental.repository.CarRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@Transactional
@Rollback
public class CarRentalServiceIntegrationTest {

    @Autowired
    private CarRentalService carRentalService;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private CarRentalRepository carRentalRepository;


    @Test
    void upsertRental() {
        Car car = new Car();
        car.setCarName("Volvo 240");
        car.setPricePerDay(new BigDecimal(5000));
        carRepository.save(car);

        Rental rental = new Rental();
        rental.setDriverName("Dennis Öberg");
        rental.setPickUpDate(TestHelper.GenerateDate(10, 20));
        rental.setReturnDate(TestHelper.GenerateDate(10, 23));
        int rentalLenght = carRentalService.getRentalLength(rental.getPickUpDate(), rental.getReturnDate());
        BigDecimal totalRentalCost = carRentalService.calculateTotalRentalCost(car.getPricePerDay(), rentalLenght);
        rental.setTotalRentalCost(totalRentalCost);
        rental.setCar(car);

        carRentalService.upsertCarRental(rental);
        List<Rental> rentals = carRentalRepository.findAll();
        Assertions.assertEquals(1, rentals.size());
        Assertions.assertEquals("Dennis Öberg", rentals.get(0));
        Assertions.assertEquals(new BigDecimal("15000.00"), rentals.get(0).getTotalRentalCost());

        String carName = rentals.get(0).getCar().getCarName();
        Assertions.assertEquals("Volvo 240", carName);
    }

}
