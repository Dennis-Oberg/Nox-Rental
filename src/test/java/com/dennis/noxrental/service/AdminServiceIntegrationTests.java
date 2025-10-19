package com.dennis.noxrental.service;

import com.dennis.noxrental.entity.DTO.AdminRentalResponseDTO;
import com.dennis.noxrental.TestHelper;
import com.dennis.noxrental.entity.Rental;
import com.dennis.noxrental.repository.CarRentalRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class AdminServiceIntegrationTests {

    @Autowired
    private AdminService adminService;
    @Autowired
    private CarRentalRepository carRentalRepository;

    @Test
    public void assertCorrectObjectIsCreatedOnListAllRequest() {
        List<Rental> rentals = TestHelper.GenerateListOfRentalData();
        for (Rental r : rentals) {
            carRentalRepository.insertRental(r.getDriverName(), r.getPickUpDate(), r.getReturnDate(), r.getTotalRentalCost(), r.getCar().getId());
        }
        AdminRentalResponseDTO result = adminService.getAdminRentalSummary();

        List<Rental> rentalsResult = result.getRentals();
        Assertions.assertEquals(2, rentalsResult.size());

        double expectedTotal = rentals.get(0).getTotalRentalCost() + rentals.get(1).getTotalRentalCost();
        Assertions.assertEquals(expectedTotal, result.getTotalRevenue(), 0.001);
    }
}
