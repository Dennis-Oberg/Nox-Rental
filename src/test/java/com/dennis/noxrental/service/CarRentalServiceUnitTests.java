package com.dennis.noxrental.service;

import com.dennis.noxrental.entity.DTO.RentalRequestDTO;
import com.dennis.noxrental.TestHelper;
import com.dennis.noxrental.constant.ErrorConstants;
import com.dennis.noxrental.entity.Car;
import com.dennis.noxrental.entity.Rental;
import com.dennis.noxrental.repository.CarRentalRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CarRentalServiceUnitTests {

    private CarRentalService carRentalService;
    private CarRentalRepository carRentalRepository;

    @BeforeEach
    public void setup() {
        carRentalRepository = mock(CarRentalRepository.class);
        Clock fixedTime = Clock.fixed(TestHelper.GenerateDate(10, 19)
                .atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());

        carRentalService = new CarRentalServiceImpl(carRentalRepository, fixedTime);
    }

    @Test
    public void assertCarIsAvailable() {
        LocalDate pickUp = TestHelper.GenerateDate(10, 20);
        LocalDate returnDate = TestHelper.GenerateDate(10, 20);

        long carId = 1L;

        when(carRentalRepository.isCarAvailable(pickUp, returnDate, carId)).thenReturn(true);
        boolean available = carRentalService.isCarAvailableForRental(pickUp, returnDate, carId);
        assertTrue(available);

        verify(carRentalRepository, times(1)).isCarAvailable(pickUp, returnDate, carId);
    }

    //dont group assertions in one method but for this one i think its fine
    @Test
    public void assertAgeOfDriver() {
        boolean driverOfAgeBelow18 = carRentalService.isDriverOfAge(17);
        assertFalse(driverOfAgeBelow18);

        boolean driverOfAgeIs18 = carRentalService.isDriverOfAge(18);
        assertTrue(driverOfAgeIs18);

        boolean driverIsAbove18 = carRentalService.isDriverOfAge(28);
        assertTrue(driverIsAbove18);
    }

    @Test
    public void assertPriceCalculation() {
        RentalRequestDTO rentalDTO = new RentalRequestDTO();
        rentalDTO.setPickUpDate(TestHelper.GenerateDate(10, 10));
        rentalDTO.setReturnDate(TestHelper.GenerateDate(10, 12));
        rentalDTO.setDriverName("Dennis");
        Car car = new Car();
        double price = 1333D;
        car.setPricePerDay(price);

        Rental rentalInstance = carRentalService.createRentalInstance(rentalDTO, car);

        assertEquals(2666D, rentalInstance.getTotalRentalCost());
    }

    @Test
    public void assertThrowsIllegalArgumentExceptionIfPriceIsNull() {
        RentalRequestDTO rentalDTO = new RentalRequestDTO();
        rentalDTO.setPickUpDate(null);
        rentalDTO.setReturnDate(TestHelper.GenerateDate(10, 10));

        IllegalArgumentException expected = Assertions.assertThrows(IllegalArgumentException.class,
                () -> carRentalService.createRentalInstance(rentalDTO, new Car()));

        assertEquals(ErrorConstants.MISSING_PICK_UP_DAY, expected.getMessage());
    }

    //effectively testing the side effect of "how many days are rented"
    @Test
    public void assertThrowsIllegalArgumentExceptionIfDayAreZeroOrLess() {
        double price = 1333D;

        RentalRequestDTO rentalDto = new RentalRequestDTO();
        rentalDto.setPickUpDate(TestHelper.GenerateDate(10, 10));
        rentalDto.setReturnDate(TestHelper.GenerateDate(10, 10));

        Car car = new Car();
        car.setPricePerDay(price);

        IllegalArgumentException expected = Assertions.assertThrows(IllegalArgumentException.class,
                () -> carRentalService.createRentalInstance(rentalDto, car));

        assertEquals(ErrorConstants.MUST_BE_ABOVE_ZERO_DAYS_RENTED, expected.getMessage());
    }

    @Test
    public void assertTrueIfDateAfterToday() {
        LocalDate pickUpDate = TestHelper.GenerateDate(10, 22);
        boolean pickUpDatePossible = carRentalService.assertPickUpDateIsNotInThePast(pickUpDate);
        assertTrue(pickUpDatePossible);
    }

    @Test
    public void assertPickUpDateIsntInThePast() {
        LocalDate pickUpDate = TestHelper.GenerateDate(10, 19);
        boolean pickUpDatePossible = carRentalService.assertPickUpDateIsNotInThePast(pickUpDate);
        assertTrue(pickUpDatePossible);
    }

    @Test
    public void assertFalseIfDateIsBeforeToday() {
        LocalDate pickUpDate = TestHelper.GenerateDate(10, 16);
        boolean pickUpDatePossible = carRentalService.assertPickUpDateIsNotInThePast(pickUpDate);
        assertFalse(pickUpDatePossible);
    }

    @Test
    public void assertDriverNameIsOk() {
        String driverName = "Dempan";
        boolean nameIsFine = carRentalService.isValidDriverName(driverName);
        assertTrue(nameIsFine);
    }

    @Test
    public void assertFalseIfDrivernameIsnull() {
        String driverName = null;
        boolean nameIsFine = carRentalService.isValidDriverName(driverName);
        assertFalse(nameIsFine);
    }

    @Test
    public void assertFalseIfDrivernameIsEmpty() {
        String driverName = "";
        boolean nameIsFine = carRentalService.isValidDriverName(driverName);
        assertFalse(nameIsFine);
    }

    @Test
    public void assertIsValidDateRanges() {
        LocalDate pickUpDate = TestHelper.GenerateDate(10, 20);
        LocalDate returnDate = TestHelper.GenerateDate(10, 21);
        assertTrue(carRentalService.isValidDatesRange(pickUpDate, returnDate));
    }

    @Test
    public void assertFalseWhenPickUpBefore19th() {
        LocalDate pickUpDate = TestHelper.GenerateDate(10, 18);
        LocalDate returnDate = TestHelper.GenerateDate(10, 21);
        assertFalse(carRentalService.isValidDatesRange(pickUpDate, returnDate));
    }

    @Test
    public void assertFalseWhenPickUpAndReturnOnTheSameDay() {
        LocalDate pickUpDate = TestHelper.GenerateDate(10, 21);
        LocalDate returnDate = TestHelper.GenerateDate(10, 21);
        assertFalse(carRentalService.isValidDatesRange(pickUpDate, returnDate));
    }

}
