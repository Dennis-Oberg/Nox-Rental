package com.dennis.noxrental.util;

import com.dennis.noxrental.entity.Car;
import com.dennis.noxrental.repository.CarRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DatabaseSeeder {

    //should use service? think its fine for this case to call the repo directly.
    @Bean
    CommandLineRunner seed(CarRepository carRepository) {
        return args -> {
            if (carRepository.count() == 0) {
                Car volvo = new Car();
                volvo.setCarName("Volvo S60");
                volvo.setPricePerDay(1500D);

                Car vw = new Car();
                vw.setCarName("Volkswagen Golf");
                vw.setPricePerDay(1333D);

                Car mustang = new Car();
                mustang.setCarName("Ford Mustang");
                mustang.setPricePerDay(3000D);

                Car transit = new Car();
                transit.setCarName("Ford Transit");
                transit.setPricePerDay(2400D);

                List<Car> cars = List.of(volvo, vw, mustang, transit);
                cars.forEach(car -> carRepository.insertCar(car.getCarName(), car.getPricePerDay()));
            }
        };
    }
}
