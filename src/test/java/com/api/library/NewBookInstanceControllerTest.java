package com.api.library;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class NewBookInstanceControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    @DisplayName("It should return 201 if a new book instance is created")
    void itShouldReturn201IfANewBookInstanceIsCreated() throws Exception {

        String isbn = "978-85-216-0378-8";
        Map<String, Object> request = Map.of("title", "Jogos Vorazes",
                                             "price", 29.95,
                                             "isbn", isbn);
        mockMvc.perform(post("/books").contentType(APPLICATION_JSON)
                                      .content(mapper.writeValueAsString(request)))
               .andExpect(status().isCreated());

        mockMvc.perform(post("/books/{bookIsbn}/instances", isbn)
                            .contentType(APPLICATION_JSON)
                            .content(mapper.writeValueAsString(Map.of("circulationType", "RESTRICTED"))))
               .andExpect(status().isCreated());

        mockMvc.perform(post("/books/{bookIsbn}/instances", isbn)
                            .contentType(APPLICATION_JSON)
                            .content(mapper.writeValueAsString(Map.of("circulationType", "CIRCULATING"))))
               .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("It should return 404 if has no book with passed isbn")
    void itShouldReturn404IfHasNoBookWithPassedIsbn() throws Exception {
        mockMvc.perform(post("/books/{bookIsbn}/instances", "1111")
                                .contentType(APPLICATION_JSON)
                                .content(mapper.writeValueAsString(Map.of("circulationType", "CIRCULATING"))))
               .andExpect(status().isNotFound());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "CIRCULAAA"})
    @DisplayName("It should return 400 if circulationType is not valid")
    void itShouldReturn400IfCirculationTypeIsNotValid(String circulationType) throws Exception {
        mockMvc.perform(post("/books/{bookIsbn}/instances", "11111")
                                .contentType(APPLICATION_JSON)
                                .content(mapper.writeValueAsString(Map.of("circulationType", circulationType))))
               .andExpect(status().isBadRequest());
    }
}