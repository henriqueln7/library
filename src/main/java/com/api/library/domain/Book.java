package com.api.library.domain;

import org.hibernate.validator.constraints.ISBN;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ISBN(type = ISBN.Type.ANY)
    @NotNull
    @Column(unique = true)
    private String isbn;
    @NotBlank
    private String title;
    @NotNull @Positive
    private BigDecimal price;
    @OneToMany(mappedBy = "book", cascade = CascadeType.MERGE)
    private final List<BookInstance> bookInstances = new ArrayList<>();
    @Deprecated
    protected Book(){}

    public Book(@ISBN(type = ISBN.Type.ANY) @NotNull String isbn, @NotBlank String title, @NotNull @Positive BigDecimal price) {
        this.isbn = isbn;
        this.title = title;
        this.price = price;
    }

    public void addBookInstance(@NotNull CirculationType circulationType) {
        Assert.notNull(circulationType, "#BUG CirculationType is null");
        BookInstance newBookInstance = new BookInstance(this, circulationType);
        this.bookInstances.add(newBookInstance);
    }

    @Override
    public String toString() {
        return "Book{" + "id=" + id + ", isbn='" + isbn + '\'' + ", title='" + title + '\'' + ", price=" + price + ", bookInstances=" + bookInstances + '}';
    }

    public Long getId() {
        return this.id;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }
}
