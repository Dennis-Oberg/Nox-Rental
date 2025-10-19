package com.dennis.noxrental.service;

import com.dennis.noxrental.constant.ErrorConstants;
import com.dennis.noxrental.entity.Car;
import com.dennis.noxrental.repository.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    private final Logger log = LoggerFactory.getLogger(CarService.class);
    private final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public List<Car> listCars() {
        return carRepository.listCars();
    }

    @Override
    public Car getCarById(long id) {
        Car carById = carRepository.getCarById(id);
        if (carById == null) {
            log.error("Didnt find any matching car with id {}\n", id);
            throw new EntityNotFoundException(ErrorConstants.CAR_NOT_FOUND);
        }
        return carById;
    }
}
