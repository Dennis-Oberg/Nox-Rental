package com.dennis.noxrental.service;

import com.dennis.noxrental.entity.Car;
import com.dennis.noxrental.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService{

    private final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public List<Car> listCars() {
        return carRepository.listCars();
    }
}
