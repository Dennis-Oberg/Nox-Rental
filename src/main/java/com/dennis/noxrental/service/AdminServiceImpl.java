package com.dennis.noxrental.service;

import com.dennis.noxrental.entity.DTO.AdminRentalResponseDTO;
import com.dennis.noxrental.entity.Rental;
import com.dennis.noxrental.repository.CarRentalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService{

    private final CarRentalRepository rentalRepository;

    public AdminServiceImpl(CarRentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }
    private double calculateTotal(List<Rental> input) {
        return input.stream().mapToDouble(Rental::getTotalRentalCost).sum();
    }
    @Override
    public AdminRentalResponseDTO getAdminRentalSummary() {
        List<Rental> all = this.rentalRepository.getAll();
        AdminRentalResponseDTO adminRentalDTO = new AdminRentalResponseDTO();
        adminRentalDTO.setRentals(all);
        adminRentalDTO.setTotalRevenue(calculateTotal(all));
        return adminRentalDTO;
    }
}
