package com.bootcamp.melifrescos.controller;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BatchControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void create_givenABatchDTO_returnsACorrectResponseEntity() throws Exception {
        ResultActions response = mockMvc.perform(
                post("/api/v1/fresh-products/batch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"productId\": 1,\n" +
                                "    \"currentTemperature\": 10.00,\n" +
                                "    \"productQuantity\": 5,\n" +
                                "    \"manufacturingDate\": \"10-11-2022\",\n" +
                                "    \"manufacturingTime\": \"12:00:00\",\n" +
                                "    \"volume\": 40.00,\n" +
                                "    \"dueDate\": \"10-01-2023\",\n" +
                                "    \"price\": 3.50\n" +
                                "}")
        );

        response.andExpect(status().isCreated());
        response.andExpect(jsonPath("$.productId", CoreMatchers.is(1)));
    }

    @Test
    public void getAllByDueDateAndCategory_givenANumberOfDaysAndAType_returnACorrectResponseEntity() throws Exception {
        ResultActions response = mockMvc.perform(
                get("/api/v1/fresh-products/batch?days=60&type=ff")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", CoreMatchers.is(10)));
    }

    @Test
    public void getAllBatchesBySector_givenANumberOfDaysAndAType_returnACorrectResponseEntity() throws Exception {
        ResultActions response = mockMvc.perform(
                get("/api/v1/fresh-products/batch/due-date?sectorId=1&numberOfDays=70")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", CoreMatchers.is(1)))
                .andExpect(jsonPath("$[0].productId", CoreMatchers.is(5)));
    }
}
