package com.dennis.noxrental.controller;

import com.dennis.noxrental.repository.CarRentalRepository;
import com.dennis.noxrental.repository.CarRepository;
import com.dennis.noxrental.service.CarRentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Transactional
@Rollback
public class CarRentalControllerIntegrationTests {

    @Autowired
    private CarRentalService carRentalService;

    @Autowired
    private CarRentalRepository carRentalRepository;

    @Autowired
    private CarRepository carRepository;


}
