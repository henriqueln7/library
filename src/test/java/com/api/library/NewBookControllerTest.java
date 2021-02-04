package com.api.library;

import com.api.library.usecases.registernewbook.NewBookRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        Map<String, Object> request = Map.of("title", "Jogos Vorazes",
                                             "price", 29.95,
                                             "isbn", "978-85-216-0378-8");
        mockMvc.perform(post("/books").contentType(MediaType.APPLICATION_JSON)
                                      .content(mapper.writeValueAsString(request)))
               .andExpect(status().isCreated());
    }

    @ParameterizedTest
    @CsvSource({
            "title, 29.95, invalid-isbn",
            "title, 29.95, ",
            "title, 0, 978-85-216-0378-8",
            "title, -1, 978-85-216-0378-8",
            "title, , 978-85-216-0378-8",
            ", 29.95, 978-85-216-0378-8",
            "'', 29.95, 978-85-216-0378-8",
            " '    ', 29.95, 978-85-216-0378-8"
    })
    @DisplayName("It should return status 400 if input data is invalid")
    void itShouldReturnStatus400IfInputDataIsInvalid(String title, BigDecimal price, String isbn) throws Exception {
        NewBookRequest request = new NewBookRequest(isbn, title, price);
        mockMvc.perform(post("/books")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(request)))
               .andExpect(status().isBadRequest());
    }
}