package com.dennis.noxrental.service;

import com.dennis.noxrental.TestHelper;
import com.dennis.noxrental.constant.ErrorConstants;
import com.dennis.noxrental.repository.CarRentalRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;
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
    public void assertCarIsAvailable(){
        LocalDate pickUp = TestHelper.GenerateDate(10,20);
        LocalDate returnDate = TestHelper.GenerateDate(10,20);

        long carId = 1L;

        when(carRentalRepository.isCarAvailable(pickUp, returnDate, carId)).thenReturn(true);
        boolean available = carRentalService.isCarAvailableForRental(pickUp, returnDate, carId);
        Assertions.assertTrue(available);

        verify(carRentalRepository, times(1)).isCarAvailable(pickUp, returnDate, carId);
    }

    @Test
    public void assertCarIsNotAvailable(){
        LocalDate pickUp = null;

    }

    //dont group assertions in one method but for this one i think its fine
    @Test
    public void assertAgeOfDriver() {
        boolean driverOfAgeBelow18 = carRentalService.isDriverOfAge(17);
        Assertions.assertFalse(driverOfAgeBelow18);

        boolean driverOfAgeIs18 = carRentalService.isDriverOfAge(18);
        Assertions.assertTrue(driverOfAgeIs18);

        boolean driverIsAbove18 = carRentalService.isDriverOfAge(28);
        Assertions.assertTrue(driverIsAbove18);
    }

    @Test
    public void assertPriceCalculation() {
        BigDecimal price = new BigDecimal(1333);
        int days = 2;
        BigDecimal bigDecimal = carRentalService.calculateTotalRentalCost(price, days);
        Assertions.assertEquals(BigDecimal.valueOf(2666), bigDecimal);
    }

    @Test
    public void assertThrowsIllegalArgumentExceptionIfPriceIsNull() {
        BigDecimal price = null;
        int days = 2;
        IllegalArgumentException expected = Assertions.assertThrows(IllegalArgumentException.class,
                () -> carRentalService.calculateTotalRentalCost(price, days));
        Assertions.assertEquals(ErrorConstants.MISSING_DAILY_PRICE, expected.getMessage());
    }

    @Test
    public void assertThrowsIllegalArgumentExceptionIfDayAreZeroOrLess() {
        BigDecimal price = new BigDecimal(1333);
        int days = 0;
        IllegalArgumentException expected = Assertions.assertThrows(IllegalArgumentException.class,
                () -> carRentalService.calculateTotalRentalCost(price, days));
        Assertions.assertEquals(ErrorConstants.MUST_BE_ABOVE_ZERO_DAYS_RENTED, expected.getMessage());
    }

    @Test
    public void assertCalculatesDayRangeCorrectly() {
        LocalDate pickUpDate = TestHelper.GenerateDate(10, 10);
        LocalDate returnDate = TestHelper.GenerateDate(10, 12);
        assertThat(carRentalService.getRentalLength(pickUpDate, returnDate)).isEqualTo(2);
    }

    @Test
    public void assertThrowsIfpickUpDayIsNull() {
        LocalDate pickUpDate = null;
        LocalDate returnDate = TestHelper.GenerateDate(10, 12);
        IllegalArgumentException expectedThrow =
                Assertions.assertThrows(IllegalArgumentException.class,
                        () -> carRentalService.getRentalLength(pickUpDate, returnDate));

        Assertions.assertEquals(ErrorConstants.MISSING_PICK_UP_DAY,expectedThrow.getMessage());
    }

    @Test
    public void assertThrowsIfreturnDayIsNull() {
        LocalDate pickUpDate = TestHelper.GenerateDate(10, 12);
        LocalDate returnDate = null;
        IllegalArgumentException expectedThrow =
                Assertions.assertThrows(IllegalArgumentException.class,
                        () -> carRentalService.getRentalLength(pickUpDate, returnDate));
        Assertions.assertEquals(ErrorConstants.MISSING_RETURN_DAY, expectedThrow.getMessage());
    }

    @Test
    public void assertTrueIfDateAfterToday() {
        LocalDate pickUpDate = TestHelper.GenerateDate(10, 22);
        boolean pickUpDatePossible = carRentalService.assertPickUpDateIsNotInThePast(pickUpDate);
        Assertions.assertTrue(pickUpDatePossible);
    }

    @Test
    public void assertPickUpDateIsntInThePast() {
        LocalDate pickUpDate = TestHelper.GenerateDate(10, 19);
        boolean pickUpDatePossible = carRentalService.assertPickUpDateIsNotInThePast(pickUpDate);
        Assertions.assertTrue(pickUpDatePossible);
    }

    @Test
    public void assertFalseIfDateIsBeforeToday() {
        LocalDate pickUpDate = TestHelper.GenerateDate(10, 16);
        boolean pickUpDatePossible = carRentalService.assertPickUpDateIsNotInThePast(pickUpDate);
        Assertions.assertFalse(pickUpDatePossible);
    }

    @Test
    public void assertDriverNameIsOk() {
        String driverName = "Dempan";
        boolean nameIsFine = carRentalService.isValidDriverName(driverName);
        Assertions.assertTrue(nameIsFine);
    }

    @Test
    public void assertFalseIfDrivernameIsnull() {
        String driverName = null;
        boolean nameIsFine = carRentalService.isValidDriverName(driverName);
        Assertions.assertFalse(nameIsFine);
    }

    @Test
    public void assertFalseIfDrivernameIsEmpty() {
        String driverName = "";
        boolean nameIsFine = carRentalService.isValidDriverName(driverName);
        Assertions.assertFalse(nameIsFine);
    }

}
