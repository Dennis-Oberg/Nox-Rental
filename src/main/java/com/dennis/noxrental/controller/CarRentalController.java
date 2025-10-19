package com.dennis.noxrental.controller;

import com.dennis.noxrental.constant.AppConstants;
import com.dennis.noxrental.constant.ErrorConstants;
import com.dennis.noxrental.entity.Car;
import com.dennis.noxrental.entity.DTO.RentalRequestDTO;
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

import javax.persistence.EntityNotFoundException;
import java.util.Map;

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
    public ResponseEntity<Map<String, Object>> rent(@RequestBody RentalRequestDTO request) {

        if (!carRentalService.isDriverOfAge(request.getDriverAge())) {
            log.info("Driver is not old enough.");
            return ResponseEntity.badRequest().body(Map.of(AppConstants.API_ERROR_RESPONSE_KEY, ErrorConstants.DRIVER_TOO_YOUNG));
        }

        if (!carRentalService.isValidDriverName(request.getDriverName())) {
            log.info("Bad driver name.");
            return ResponseEntity.badRequest().body(Map.of(AppConstants.API_ERROR_RESPONSE_KEY, ErrorConstants.BAD_DRIVER_NAME_INPUT));
        }

        if (!carRentalService.isValidDatesRange(request.getPickUpDate(), request.getReturnDate())) {
            return ResponseEntity.badRequest().body(Map.of(AppConstants.API_ERROR_RESPONSE_KEY, ErrorConstants.INVALID_DATE_RANGE));
        }

        //move to some global error interceptor
        Car requestedCar;
        try {
            requestedCar = carService.getCarById(request.getCarId());
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(AppConstants.API_ERROR_RESPONSE_KEY, ErrorConstants.CAR_NOT_FOUND));
        }

        //asserts if available between these dates.
        boolean isCarAvailable = carRentalService.isCarAvailableForRental(request.getPickUpDate(), request.getReturnDate(), requestedCar.getId());
        if (!isCarAvailable) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(AppConstants.API_ERROR_RESPONSE_KEY, ErrorConstants.CAR_NOT_AVAILABLE));
        }

        Rental rental = carRentalService.createRentalInstance(request, requestedCar);
        carRentalService.upsertCarRental(rental);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(AppConstants.API_DATA_RESPONSE_KEY, "Rental created!"));
    }
}
