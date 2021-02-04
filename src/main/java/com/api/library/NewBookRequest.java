package com.api.library;

import org.hibernate.validator.constraints.ISBN;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


import java.math.BigDecimal;

public class NewBookRequest {
    @NotNull
    @ISBN(type = ISBN.Type.ANY)
    public final String isbn;
    @NotBlank
    public final String title;
    @NotNull
    @Positive
    public final BigDecimal price;

    public NewBookRequest(@NotNull @ISBN(type = ISBN.Type.ANY) String isbn, @NotBlank String title, @NotNull @Positive BigDecimal price) {
        this.isbn = isbn;
        this.title = title;
        this.price = price;
    }

    public Book newBook() {
        return new Book(this.isbn, this.title, this.price);
    }
}
