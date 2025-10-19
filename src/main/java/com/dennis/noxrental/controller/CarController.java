package com.dennis.noxrental.controller;

import com.dennis.noxrental.constant.AppConstants;
import com.dennis.noxrental.entity.Car;
import com.dennis.noxrental.service.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/list")
    public ResponseEntity<Map<String, List<Car>>> listCars() {
        return ResponseEntity.ok().body(Map.of(AppConstants.API_DATA_RESPONSE_KEY, carService.listCars()));
    }

}
