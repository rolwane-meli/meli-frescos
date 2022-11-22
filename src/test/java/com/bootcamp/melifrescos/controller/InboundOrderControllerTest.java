package com.bootcamp.melifrescos.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class InboundOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void create_givenAValidInboundOrderDTO_returnsACorrectResponseEntity() throws Exception {
        ResultActions response = mockMvc.perform(
                post("/api/v1/fresh-products/inboundorder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"sectionCode\": 1,\n" +
                                "    \"orderDate\": \"15-11-2022\",\n" +
                                "    \"batchStock\": [\n" +
                                "        {\n" +
                                "            \"productId\": 6,\n" +
                                "            \"currentTemperature\": -18,\n" +
                                "            \"productQuantity\": 15,\n" +
                                "            \"manufacturingDate\": \"10-11-2022\",\n" +
                                "            \"manufacturingTime\": \"08:21:22\",\n" +
                                "            \"volume\": 4,\n" +
                                "            \"dueDate\": \"10-12-2022\",\n" +
                                "            \"price\": 18.5\n" +
                                "        }\n" +
                                "    ]\n" +
                                "}")
        );

        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber());
    }

    @Test
    public void update_givenAValidInboundOrderDTO_returnsACorrectResponseEntity() throws Exception {
        ResultActions response = mockMvc.perform(
                put("/api/v1/fresh-products/inboundorder/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"sectionCode\": 1,\n" +
                                "    \"orderDate\": \"15-11-2022\",\n" +
                                "    \"batchStock\": [\n" +
                                "        {\n" +
                                "            \"productId\": 6,\n" +
                                "            \"currentTemperature\": -18,\n" +
                                "            \"productQuantity\": 15,\n" +
                                "            \"manufacturingDate\": \"10-11-2022\",\n" +
                                "            \"manufacturingTime\": \"08:21:22\",\n" +
                                "            \"volume\": 4,\n" +
                                "            \"dueDate\": \"10-12-2022\",\n" +
                                "            \"price\": 18.5\n" +
                                "        }\n" +
                                "    ]\n" +
                                "}")
        );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber());
    }
}
