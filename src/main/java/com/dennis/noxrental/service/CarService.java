package com.dennis.noxrental.service;

import com.dennis.noxrental.entity.Car;

import java.util.List;

public interface CarService {

    List<Car> listCars();

    Car getCarById(long id);
}
