package com.api.library;

import org.hibernate.validator.constraints.ISBN;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ISBN(type = ISBN.Type.ANY)
    private String isbn;
    @NotBlank
    private String title;
    @NotNull @Positive
    private BigDecimal price;

    @Deprecated
    protected Book(){}

    public Book(@ISBN(type = ISBN.Type.ANY) String isbn, @NotBlank String title, @NotNull @Positive BigDecimal price) {
        this.isbn = isbn;
        this.title = title;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" + "id=" + id + ", isbn='" + isbn + '\'' + ", title='" + title + '\'' + ", price=" + price + '}';
    }
}
