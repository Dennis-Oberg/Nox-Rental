package com.dennis.noxrental.service;

import com.dennis.noxrental.entity.DTO.RentalRequestDTO;
import com.dennis.noxrental.entity.Car;
import com.dennis.noxrental.entity.Rental;

import java.time.LocalDate;

public interface CarRentalService {
    boolean isDriverOfAge(int age);

    boolean assertPickUpDateIsNotInThePast(LocalDate pickUpDate);

    boolean isCarAvailableForRental(LocalDate pickUpDate, LocalDate returnDate, long carId);

    void upsertCarRental(Rental rental);

    boolean isValidDriverName(String driverName);

    boolean isValidDatesRange(LocalDate pickUpDate, LocalDate returnDate);

    Rental createRentalInstance(RentalRequestDTO dto, Car car);
}
