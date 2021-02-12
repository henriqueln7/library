package com.api.library.domain;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
public class BookInstance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull @Valid
    @ManyToOne
    private Book book;
    @NotNull
    @Enumerated(EnumType.STRING)
    private CirculationType circulationType;

    @Deprecated
    protected BookInstance(){}

    public BookInstance(@NotNull @Valid Book book, @NotNull CirculationType circulationType) {
        this.book = book;
        this.circulationType = circulationType;
    }

    @Override
    public String toString() {
        return "BookInstance{" + "id=" + id + ", circulationType=" + circulationType + '}';
    }

    public boolean acceptToBeHoldTo(Patron patron) {
        if (this.circulationType.equals(CirculationType.CIRCULATING)) {
            return true;
        }

        return patron.researcher();
    }
}
