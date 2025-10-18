package com.dennis.noxrental.controller;


import com.dennis.noxrental.TestHelper;
import com.dennis.noxrental.service.CarService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static com.dennis.noxrental.TestHelper.LIST_ALL_CARS_URL;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class CarControllerUnitTests {

    @MockBean
    private CarService carService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldGiveBackListOffourItems() throws Exception {
        when(carService.listCars()).thenReturn(TestHelper.GenerateCarListTestData());
        this.mockMvc.perform(get(LIST_ALL_CARS_URL)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].carName", is("Volvo S60")))
                .andExpect(jsonPath("$[0].pricePerDay", is(1500)))

                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].carName", is("Volkswagen Golf")))
                .andExpect(jsonPath("$[1].pricePerDay", is(1333)))

                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].carName", is("Ford Mustang")))
                .andExpect(jsonPath("$[2].pricePerDay", is(3000)))

                .andExpect(jsonPath("$[3].id", is(4)))
                .andExpect(jsonPath("$[3].carName", is("Ford Transit")))
                .andExpect(jsonPath("$[3].pricePerDay", is(2400)));
    }
}
