package com.dennis.noxrental.service;

import com.dennis.noxrental.entity.Car;

import java.util.List;

public interface CarService {
    void insertCar(String carName, double pricePerDay);

    List<Car> listCars();

    Car getCarById(long id);
}
