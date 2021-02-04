package com.api.library;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc
class NewBookControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    @DisplayName("It should return status 201 if input data is valid (ISBN, Price and title)")
    void itShouldReturnStatus201IfInputDataIsValidIsbnPriceAndTitle() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/books")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(mapper.writeValueAsString(Map.of("title", "Jogos Vorazes", "price", 29.95, "ISBN", "978-85-216-0378-8"))))
               .andExpect(MockMvcResultMatchers.status().isCreated());
    }
}