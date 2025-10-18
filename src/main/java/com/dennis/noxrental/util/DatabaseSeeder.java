package com.dennis.noxrental.util;

import com.dennis.noxrental.entity.Car;
import com.dennis.noxrental.repository.CarRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class DatabaseSeeder {
    @Bean
    CommandLineRunner seed(CarRepository carRepository) {
        return args -> {
            if (carRepository.count() == 0) {
                Car volvo = new Car();
                volvo.setCarName("Volvo S60");
                volvo.setPricePerDay(new BigDecimal("1500"));

                Car vw = new Car();
                vw.setCarName("Volkswagen Golf");
                vw.setPricePerDay(new BigDecimal( "1333"));

                Car mustang = new Car();
                mustang.setCarName("Ford Mustang");
                mustang.setPricePerDay(new BigDecimal( "3000"));

                Car transit = new Car();
                transit.setCarName("Ford Transit");
                transit.setPricePerDay(new BigDecimal( "2400"));

                List<Car> cars = List.of(volvo, vw, mustang, transit);
                cars.forEach(car -> carRepository.insertCar(car.getCarName(), car.getPricePerDay()));
            }
        };
    }

}
