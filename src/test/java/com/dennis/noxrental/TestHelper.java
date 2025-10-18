package com.dennis.noxrental;


import com.dennis.noxrental.entity.Car;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class TestHelper {

    public static final String LIST_ALL_CARS_URL = "/api/v1/cars/list";
    private final static int DEFAULT_YEAR = 2025;

    public static List<Car> GenerateCarListTestData() {
        Car volvo = new Car();
        volvo.setId(1L);
        volvo.setCarName("Volvo S60");
        volvo.setPricePerDay(new BigDecimal(1500));


        Car vw = new Car();
        vw.setId(2L);
        vw.setCarName("Volkswagen Golf");

        vw.setPricePerDay(new BigDecimal(1333));


        Car ford = new Car();
        ford.setId(3L);
        ford.setCarName("Ford Mustang");

        ford.setPricePerDay(new BigDecimal(3000));

        Car transit = new Car();
        transit.setId(4L);
        transit.setCarName("Ford Transit");
        transit.setPricePerDay(new BigDecimal(2400));


        return List.of(volvo, vw, ford, transit);
    }

    public static LocalDate GenerateDate(int month, int day) {
        return LocalDate.of(DEFAULT_YEAR, month, day);
    }
}
