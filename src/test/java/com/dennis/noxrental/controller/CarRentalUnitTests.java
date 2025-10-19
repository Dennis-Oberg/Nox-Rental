package com.dennis.noxrental.controller;


import com.dennis.noxrental.entity.DTO.RentalRequestDTO;
import com.dennis.noxrental.TestHelper;
import com.dennis.noxrental.constant.ErrorConstants;
import com.dennis.noxrental.entity.Car;
import com.dennis.noxrental.service.CarRentalService;
import com.dennis.noxrental.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityNotFoundException;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarRentalUnitTests {

    @Mock
    private CarRentalService carRentalService;

    @Mock
    private CarService carService;

    @InjectMocks
    private CarRentalController carRentalController;

    private RentalRequestDTO request;

    @BeforeEach
    public void setup() {
        request = new RentalRequestDTO();
        request.setDriverAge(25);
        request.setDriverName("Denniz");
        request.setPickUpDate(TestHelper.GenerateDate(10, 20));
        request.setReturnDate(TestHelper.GenerateDate(10, 20));
        request.setCarId(1L);
    }

    @Test
    public void assertSuccessRentalRequest() {
        Car car = new Car();
        car.setId(1L);
        double defaultPrice = 1500D;
        car.setPricePerDay(defaultPrice);

        when(carRentalService.isDriverOfAge(25)).thenReturn(true);
        when(carRentalService.isValidDriverName("Denniz")).thenReturn(true);
        when(carRentalService.isValidDatesRange(any(), any())).thenReturn(true);
        when(carService.getCarById(1L)).thenReturn(car);
        when(carRentalService.isCarAvailableForRental(any(), any(), eq(1L))).thenReturn(true);

        ResponseEntity<Map<String, Object>> response = carRentalController.rent(request);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Rental created!", Objects.requireNonNull(response.getBody()).get("data"));
        verify(carRentalService).upsertCarRental(any());
    }

    @Test
    public void assertFailedIfDriverLowAge() {
        when(carRentalService.isDriverOfAge(anyInt())).thenReturn(false);
        ResponseEntity<Map<String, Object>> response = carRentalController.rent(request);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ErrorConstants.DRIVER_TOO_YOUNG, Objects.requireNonNull(response.getBody()).get("error"));
    }

    @Test
    public void assertFailOnCarNotFound() {
        when(carRentalService.isDriverOfAge(anyInt())).thenReturn(true);
        when(carRentalService.isValidDriverName("Denniz")).thenReturn(true);
        when(carRentalService.isValidDatesRange(any(), any())).thenReturn(true);
        when(carService.getCarById(anyLong())).thenThrow(new EntityNotFoundException(ErrorConstants.CAR_NOT_FOUND));
        ResponseEntity<Map<String, Object>> response = carRentalController.rent(request);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ErrorConstants.CAR_NOT_FOUND, Objects.requireNonNull(response.getBody()).get("error"));
    }

    @Test
    public void assertCantRentCarsOnPastDates() {
        when(carRentalService.isDriverOfAge(anyInt())).thenReturn(true);
        when(carRentalService.isValidDriverName("Denniz")).thenReturn(true);
        when(carRentalService.isValidDatesRange(any(), any())).thenReturn(false);

        ResponseEntity<Map<String, Object>> response = carRentalController.rent(request);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ErrorConstants.INVALID_DATE_RANGE, Objects.requireNonNull(response.getBody()).get("error"));
    }
}
