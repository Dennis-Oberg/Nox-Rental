package com.dennis.noxrental.controller;

import com.dennis.noxrental.TestHelper;
import com.dennis.noxrental.service.AdminService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static com.dennis.noxrental.TestHelper.GET_ALL_RENTALS_ADMIN_URL;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class AdminControllerUnitTests {

    @MockBean
    private AdminService adminService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void assertGettingCorrecJSONtLength() throws Exception {
        when(adminService.getAdminRentalSummary()).thenReturn(TestHelper.GenerateRentalListTestData());

        this.mockMvc.perform(get(GET_ALL_RENTALS_ADMIN_URL)).andDo(print())
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.rentals", hasSize(2)));
    }

    @Test
    public void assertCanReadJsonPaths() throws Exception {

        when(adminService.getAdminRentalSummary()).thenReturn(TestHelper.GenerateRentalListTestData());

        this.mockMvc.perform(get(GET_ALL_RENTALS_ADMIN_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.data.rentals", hasSize(2)))

                .andExpect(jsonPath("$.data.rentals[0].id", is(1)))
                .andExpect(jsonPath("$.data.rentals[0].pickUpDate", is("2025-01-01")))
                .andExpect(jsonPath("$.data.rentals[0].returnDate", is("2025-01-02")))
                .andExpect(jsonPath("$.data.rentals[0].car.id", is(1)))
                .andExpect(jsonPath("$.data.rentals[0].car.carName", is("Volvo S60")))
                .andExpect(jsonPath("$.data.rentals[0].car.pricePerDay", is(1500.0)))
                .andExpect(jsonPath("$.data.rentals[0].totalRentalCost", is(50.0)))
                .andExpect(jsonPath("$.data.rentals[0].driverName", is("Dennis")))

                .andExpect(jsonPath("$.data.rentals[1].id", is(2)))
                .andExpect(jsonPath("$.data.rentals[1].pickUpDate", is("2025-01-05")))
                .andExpect(jsonPath("$.data.rentals[1].returnDate", is("2025-01-06")))
                .andExpect(jsonPath("$.data.rentals[1].car.id", is(1)))
                .andExpect(jsonPath("$.data.rentals[1].car.carName", is("Volvo S60")))
                .andExpect(jsonPath("$.data.rentals[1].car.pricePerDay", is(1500.0)))
                .andExpect(jsonPath("$.data.rentals[1].totalRentalCost", is(500.0)))
                .andExpect(jsonPath("$.data.rentals[1].driverName", is("Dempa")))

                .andExpect(jsonPath("$.data.totalRevenue", is(550.0))); //not correct and not what im testing so it doesnt matter. just put something there for the json parsing itself
    }

}

