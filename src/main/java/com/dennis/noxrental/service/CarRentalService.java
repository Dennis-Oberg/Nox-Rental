package com.dennis.noxrental.service;

import com.dennis.noxrental.entity.Rental;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface CarRentalService {
    boolean isDriverOfAge(int age);

    boolean assertPickUpDateIsNotInThePast(LocalDate pickUpDate);

    BigDecimal calculateTotalRentalCost(BigDecimal pricePerDay, int days);

    int getRentalLength(LocalDate pickUpDate, LocalDate returnDate);

    boolean isCarAvailableForRental(LocalDate pickUpDate, LocalDate returnDate, long carId);

    void upsertCarRental(Rental rental);
    boolean isValidDriverName(String driverName);
}
