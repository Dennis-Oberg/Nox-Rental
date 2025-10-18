package com.dennis.noxrental.repository;

import com.dennis.noxrental.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface CarRentalRepository extends JpaRepository<Rental, Long> {

    @Modifying
    @Transactional
    @Query(value = """
            INSERT INTO rentals(driver_name, pick_up_date, return_date, total_rental_cost, car_id)
            VALUES (:driverName, :pickupDate, :returnDate, :totalCost, :carId)
            """, nativeQuery = true)
    void insertRental(
            @Param("driverName") String driverName,
            @Param("pickupDate") LocalDate pickupDate,
            @Param("returnDate") LocalDate returnDate,
            @Param("totalCost") BigDecimal totalCost,
            @Param("carId") Long carId
    );

    @Query(value = """
                        SELECT CASE
                            WHEN COUNT(r) = 0 THEN TRUE
                            else FALSE
                        END
                        FROM rentals r
                        WHERE r.car_id = :carId
                        AND r.pick_up_date <= :returnDate
                        AND r.return_date >= :pickUpDate
            """, nativeQuery = true)
    boolean isCarAvailable(@Param("pickUpDate") LocalDate pickUpDate, @Param("returnDate") LocalDate returnDate, @Param("carId") long carId);

    @Query(value = "SELECT id, FROM rentals", nativeQuery = true)
    List<Rental> getAll();

}
