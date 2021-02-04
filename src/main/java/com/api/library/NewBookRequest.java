package com.api.library;

import org.hibernate.validator.constraints.ISBN;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


import java.math.BigDecimal;

public class NewBookRequest {
    @ISBN(type = ISBN.Type.ANY)
    private final String isbn;
    @NotBlank
    private final String title;
    @NotNull
    @Positive
    private final BigDecimal price;

    public NewBookRequest(String isbn, String title, BigDecimal price) {
        this.isbn = isbn;
        this.title = title;
        this.price = price;
    }

    public Book newBook() {
        return new Book(this.isbn, this.title, this.price);
    }
}
