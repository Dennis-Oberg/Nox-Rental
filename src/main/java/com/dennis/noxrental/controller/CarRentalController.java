package com.dennis.noxrental.controller;

import com.dennis.noxrental.DTO.RentalDTO;
import com.dennis.noxrental.entity.Car;
import com.dennis.noxrental.entity.Rental;
import com.dennis.noxrental.service.CarRentalService;
import com.dennis.noxrental.service.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/rental")
public class CarRentalController {

    private static final Logger log = LoggerFactory.getLogger(CarRentalController.class);

    private final CarRentalService carRentalService;

    private final CarService carService;

    public CarRentalController(CarRentalService carRentalService, CarService carService) {
        this.carRentalService = carRentalService;
        this.carService = carService;
    }


    @PutMapping(value = "/rent")
    public ResponseEntity<String> rent() {



        Car car = new Car();
        car.setId(1L);
        car.setPricePerDay(new BigDecimal("1000"));

        Rental rental = new Rental();
        rental.setCar(car);
        rental.setPickUpDate(LocalDate.of(2025, 10,10));
        rental.setReturnDate(LocalDate.of(2025, 10,12));
        rental.setTotalRentalCost(carRentalService.calculateTotalRentalCost(car.getPricePerDay(), 2));
        rental.setDriverName("Dennis");

        carRentalService.upsertCarRental(rental);
        return new ResponseEntity<>("Rental created!", HttpStatus.CREATED);
    }

}
