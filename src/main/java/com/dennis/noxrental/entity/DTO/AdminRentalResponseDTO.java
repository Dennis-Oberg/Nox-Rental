package com.dennis.noxrental.entity.DTO;

import com.dennis.noxrental.entity.Rental;

import java.util.List;

public class AdminRentalResponseDTO {

    private List<Rental> rentals;
    private double totalRevenue;

    public List<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
