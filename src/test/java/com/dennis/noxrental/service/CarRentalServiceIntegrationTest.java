package com.dennis.noxrental.service;


import com.dennis.noxrental.entity.DTO.RentalRequestDTO;
import com.dennis.noxrental.TestHelper;
import com.dennis.noxrental.entity.Car;
import com.dennis.noxrental.entity.Rental;
import com.dennis.noxrental.repository.CarRentalRepository;
import com.dennis.noxrental.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        car.setPricePerDay(5000D);
        carRepository.save(car);

        RentalRequestDTO rentalDto = new RentalRequestDTO();
        rentalDto.setDriverName("Dennis Öberg");
        rentalDto.setPickUpDate(TestHelper.GenerateDate(10, 20));
        rentalDto.setReturnDate(TestHelper.GenerateDate(10, 23));

        Rental rentalInstance = carRentalService.createRentalInstance(rentalDto, car);

        carRentalService.upsertCarRental(rentalInstance);
        List<Rental> rentals = carRentalRepository.findAll();
        assertEquals(1, rentals.size());
        assertEquals("Dennis Öberg", rentals.get(0).getDriverName());
        assertEquals(15000.00D, rentals.get(0).getTotalRentalCost());

        String carName = rentals.get(0).getCar().getCarName();
        assertEquals("Volvo 240", carName);
    }

}
