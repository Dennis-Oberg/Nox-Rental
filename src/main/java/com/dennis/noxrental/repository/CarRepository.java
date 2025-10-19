package com.dennis.noxrental.repository;

import com.dennis.noxrental.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO cars (car_name, price_per_day) VALUES (:carName, :pricePerDay)", nativeQuery = true)
    void insertCar(@Param("carName") String carName, @Param("pricePerDay") double pricePerDay);

    @Query(value = "SELECT id, car_name, price_per_day from cars", nativeQuery = true)
    List<Car> listCars();

    @Query(value = "SELECT id, car_name, price_per_day FROM cars c where c.id = :carId", nativeQuery = true)
    Car getCarById(@Param("carId") long id);
}
