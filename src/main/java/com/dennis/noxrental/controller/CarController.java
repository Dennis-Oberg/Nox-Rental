package com.dennis.noxrental.controller;

import com.dennis.noxrental.entity.Car;
import com.dennis.noxrental.service.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Car>> listCars() {
        return new ResponseEntity<>(carService.listCars(), HttpStatus.OK);
    }

}
