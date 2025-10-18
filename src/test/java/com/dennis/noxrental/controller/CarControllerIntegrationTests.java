package com.dennis.noxrental.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static com.dennis.noxrental.TestHelper.LIST_ALL_CARS_URL;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class CarControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnOK() throws Exception {
        mockMvc.perform(get(LIST_ALL_CARS_URL))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    void shouldListAllCars() throws Exception {
        mockMvc.perform(get(LIST_ALL_CARS_URL))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
