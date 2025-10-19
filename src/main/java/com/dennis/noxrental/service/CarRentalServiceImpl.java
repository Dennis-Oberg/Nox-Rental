package com.dennis.noxrental.service;

import com.dennis.noxrental.entity.DTO.RentalRequestDTO;
import com.dennis.noxrental.constant.AppConstants;
import com.dennis.noxrental.constant.ErrorConstants;
import com.dennis.noxrental.entity.Car;
import com.dennis.noxrental.entity.Rental;
import com.dennis.noxrental.repository.CarRentalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class CarRentalServiceImpl implements CarRentalService {

    private final static Logger log = LoggerFactory.getLogger(CarRentalService.class);
    private final CarRentalRepository carRentalRepository;
    private final Clock clock;

    public CarRentalServiceImpl(CarRentalRepository carRentalRepository, Clock clock) {
        this.carRentalRepository = carRentalRepository;
        this.clock = clock;
    }

    @Override
    public boolean isDriverOfAge(int age) {
        return age >= AppConstants.MIN_AGE;
    }

    @Override
    public boolean assertPickUpDateIsNotInThePast(LocalDate pickUpDate) {
        if (pickUpDate == null) {
            throw new IllegalArgumentException(ErrorConstants.MISSING_PICK_UP_DAY);
        }
        LocalDate todaysDate = LocalDate.now(this.clock);
        return !todaysDate.isAfter(pickUpDate);
    }

    private double calculateTotalRentalCost(double pricePerDay, int days) {
        if (pricePerDay == 0) {
            throw new IllegalArgumentException(ErrorConstants.MISSING_DAILY_PRICE);
        }
        if (days <= 0) {
            throw new IllegalArgumentException(ErrorConstants.MUST_BE_ABOVE_ZERO_DAYS_RENTED);
        }
        return pricePerDay * days;
    }

    private int getRentalLength(LocalDate pickUpDate, LocalDate returnDate) {
        if (pickUpDate == null) {
            throw new IllegalArgumentException(ErrorConstants.MISSING_PICK_UP_DAY);
        }
        if (returnDate == null) {
            throw new IllegalArgumentException(ErrorConstants.MISSING_RETURN_DAY);

        }
        return (int) ChronoUnit.DAYS.between(pickUpDate, returnDate);
    }

    @Override
    public boolean isCarAvailableForRental(LocalDate pickUpDate, LocalDate returnDate, long carId) {
        if (pickUpDate == null) {
            throw new IllegalArgumentException(ErrorConstants.MISSING_PICK_UP_DAY);
        }
        if (returnDate == null) {
            throw new IllegalArgumentException(ErrorConstants.MISSING_RETURN_DAY);
        }
        return this.carRentalRepository.isCarAvailable(pickUpDate, returnDate, carId);
    }

    @Override
    public void upsertCarRental(Rental rental) {
        this.carRentalRepository.insertRental(rental.getDriverName(), rental.getPickUpDate(), rental.getReturnDate(), rental.getTotalRentalCost(), rental.getCar().getId());
    }

    //not sure about this implementation,
    //can the drivername be d3nnis? bengt2? not sure. :)
    @Override
    public boolean isValidDriverName(String driverName) {
        if (driverName == null) {
            log.error(ErrorConstants.DRIVER_NAME_IS_NULL);
            return false;
        }
        if (driverName.trim().isEmpty()) {
            log.error(ErrorConstants.EMPTY_DRIVER_NAME);
            return false;
        }
        return !driverName.matches(".*\\d.*");
    }

    @Override
    public boolean isValidDatesRange(LocalDate pickUpDate, LocalDate returnDate) {
        if (pickUpDate == null) {
            log.error("pick up date is null for range check.");
            return false;
        }
        if (returnDate == null) {
            log.error("return date is null for range check");
            return false;
        }
        if (!assertPickUpDateIsNotInThePast(pickUpDate)) {
            log.info("cant pick up the car on a past date.");
            return false;
        }
        if (!returnDate.isAfter(pickUpDate)) {
            log.info("return date must be after pick up date and cant pick up and return the car on the same day");
            return false;
        }
        return true;
    }

    @Override
    public Rental createRentalInstance(RentalRequestDTO dto, Car car) {
        int days = getRentalLength(dto.getPickUpDate(), dto.getReturnDate());
        double totalCost = calculateTotalRentalCost(car.getPricePerDay(), days);
        Rental rental = new Rental();
        rental.setTotalRentalCost(totalCost);
        rental.setDriverName(dto.getDriverName().trim());
        rental.setPickUpDate(dto.getPickUpDate());
        rental.setReturnDate(dto.getReturnDate());
        rental.setCar(car);

        return rental;
    }
}
