package com.dennis.noxrental;


import com.dennis.noxrental.entity.DTO.AdminRentalResponseDTO;
import com.dennis.noxrental.entity.Car;
import com.dennis.noxrental.entity.Rental;

import java.time.LocalDate;
import java.util.List;

public class TestHelper {

    public static final String LIST_ALL_CARS_URL = "/api/v1/cars/list";

    public static final String GET_ALL_RENTALS_ADMIN_URL = "/api/v1/admin/list";
    private final static int DEFAULT_YEAR = 2025;

    public static List<Car> GenerateCarListTestData() {
        Car volvo = new Car();
        volvo.setId(1L);
        volvo.setCarName("Volvo S60");
        volvo.setPricePerDay(1500D);

        Car vw = new Car();
        vw.setId(2L);
        vw.setCarName("Volkswagen Golf");
        vw.setPricePerDay(1333D);

        Car ford = new Car();
        ford.setId(3L);
        ford.setCarName("Ford Mustang");
        ford.setPricePerDay(3000D);

        Car transit = new Car();
        transit.setId(4L);
        transit.setCarName("Ford Transit");
        transit.setPricePerDay(2400D);

        return List.of(volvo, vw, ford, transit);
    }

    public static AdminRentalResponseDTO GenerateRentalListTestData() {
        List<Car> cars = GenerateCarListTestData();

        Rental r1 = new Rental();
        r1.setId(1L);
        r1.setCar(cars.get(0));
        r1.setTotalRentalCost(50D);
        r1.setPickUpDate(TestHelper.GenerateDate(1, 1));
        r1.setReturnDate(TestHelper.GenerateDate(1, 2));
        r1.setDriverName("Dennis");


        Rental r2 = new Rental();
        r2.setId(2L);
        r2.setCar(cars.get(0));
        r2.setTotalRentalCost(500D);
        r2.setPickUpDate(TestHelper.GenerateDate(1, 5));
        r2.setReturnDate(TestHelper.GenerateDate(1, 6));
        r2.setDriverName("Dempa");

        AdminRentalResponseDTO rentalDTO = new AdminRentalResponseDTO();
        rentalDTO.setRentals(List.of(r1, r2));
        rentalDTO.setTotalRevenue(550D);

        return rentalDTO;
    }

    public static LocalDate GenerateDate(int month, int day) {
        return LocalDate.of(DEFAULT_YEAR, month, day);
    }

    public static List<Rental> GenerateListOfRentalData() {
        List<Car> cars = GenerateCarListTestData();

        Rental r1 = new Rental();
        r1.setId(1L);
        r1.setCar(cars.get(0));
        r1.setTotalRentalCost(50D);
        r1.setPickUpDate(TestHelper.GenerateDate(1, 1));
        r1.setReturnDate(TestHelper.GenerateDate(1, 2));
        r1.setDriverName("Dennis");


        Rental r2 = new Rental();
        r2.setId(2L);
        r2.setCar(cars.get(0));
        r2.setTotalRentalCost(500D);
        r2.setPickUpDate(TestHelper.GenerateDate(1, 5));
        r2.setReturnDate(TestHelper.GenerateDate(1, 6));
        r2.setDriverName("Dempa");

        return List.of(r1, r2);
    }
}
