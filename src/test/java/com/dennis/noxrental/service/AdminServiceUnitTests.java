package com.dennis.noxrental.service;

import com.dennis.noxrental.TestHelper;
import com.dennis.noxrental.entity.DTO.AdminRentalResponseDTO;
import com.dennis.noxrental.entity.Rental;
import com.dennis.noxrental.repository.CarRentalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AdminServiceUnitTests {

    private AdminService adminService;
    private CarRentalRepository rentalRepository;

    @BeforeEach
    public void setup() {
        rentalRepository = mock(CarRentalRepository.class);
        adminService = new AdminServiceImpl(rentalRepository);
    }

    @Test
    public void assertAdminRentalDTOIsCreatedCorrectly() {
        List<Rental> mockRentals = TestHelper.GenerateListOfRentalData();
        when(rentalRepository.getAll()).thenReturn(mockRentals);

        AdminRentalResponseDTO result = adminService.getAdminRentalSummary();

        double expectedTotal = mockRentals.stream().mapToDouble(Rental::getTotalRentalCost).sum();
        assertEquals(expectedTotal, result.getTotalRevenue(), 0.001);

        assertEquals(mockRentals.size(), result.getRentals().size());
    }
}
