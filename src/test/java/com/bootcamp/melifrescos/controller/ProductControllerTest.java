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
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void create_givenAProductRequestDTO_returnsACorrectResponseEntity() throws Exception {
        ResultActions response = mockMvc.perform(
                post("/api/v1/fresh-products/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"name\": \"Ovo de Codorna\",\n" +
                                "  \"type\": \"REFRIGERATED\",\n" +
                                "  \"sellerId\": 1\n" +
                                "}")
        );

        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", CoreMatchers.is("Ovo de Codorna")))
                .andExpect(jsonPath("$.type", CoreMatchers.is("REFRIGERATED")));
    }

    @Test
    public void getAll_returnACorrectResponseEntity() throws Exception {
        ResultActions response = mockMvc.perform(
                get("/api/v1/fresh-products/product")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void getAllByType_whenThereIsAType_returnACorrectResponseEntity() throws Exception {
        ResultActions response = mockMvc.perform(
                get("/api/v1/fresh-products/product/type/FF")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].type",CoreMatchers.is("FROZEN")));
    }

    @Test
    public void getByIdWithBatches_whenTheProductExists_returnACorrectResponseEntity() throws Exception {
        ResultActions response = mockMvc.perform(
                get("/api/v1/fresh-products/product/1")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id",CoreMatchers.is(1)));
    }

    @Test
    public void getByIdWithSortedBatches_whenTheProductExists_returnACorrectResponseEntity() throws Exception {
        ResultActions response = mockMvc.perform(
                get("/api/v1/fresh-products/product/5?orderByType=Q")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id",CoreMatchers.is(5)))
                .andExpect(jsonPath("$.batchStock").isArray());
    }
}
