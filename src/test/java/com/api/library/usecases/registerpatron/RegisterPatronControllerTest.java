package com.api.library.usecases.registerpatron;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RegisterPatronControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    @DisplayName("It should return status 201 if type of patron is valid (NORMAL or RESEARCHER)")
    void itShouldReturnStatus201IfTypeOfPatronIsValid() throws Exception {
        mockMvc.perform(post("/patrons").contentType(APPLICATION_JSON)
                                        .content(mapper.writeValueAsString(Map.of("type", "normal"))))
               .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("It should return status 400 if type of patron is not valid")
    void itShouldReturnStatus400IfTypeOfPatronIsNotValid() throws Exception {
        mockMvc.perform(post("/patrons")
                                .contentType(APPLICATION_JSON)
                                .content(mapper.writeValueAsString(Map.of("type", "notvalid"))))
               .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("It should return a body with id and type of patron that was registered")
    void itShouldReturnABodyWithIdAndTypeOfPatronThatWasRegistered() throws Exception {
        mockMvc.perform(post("/patrons")
                                .contentType(APPLICATION_JSON)
                                .content(mapper.writeValueAsString(Map.of("type", "normal"))))
               .andExpect(jsonPath("$.id").isNumber())
               .andExpect(jsonPath("$.type").isString());
    }

}