package com.pl.vpp.controllers;

import com.pl.vpp.services.BatteryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(BatteryController.class)
@Import(BatteryService.class)
class BatteryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private BatteryService service;

    @Test
    void testAddBatteriesEndpoint() throws Exception {
        String requestBody = "[{\"name\":\"Battery1\",\"postcode\":\"6000\",\"capacity\":5000}]";
        mockMvc.perform(MockMvcRequestBuilders.post("/batteries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetBatteriesByPostcodeRangeEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/batteries?startPostcode=6000&endPostcode=6100"))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
