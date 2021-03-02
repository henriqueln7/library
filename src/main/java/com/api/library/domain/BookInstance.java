package com.api.library.domain;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

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
    @OneToMany(mappedBy = "bookInstance")
    private final List<Hold> holds = new ArrayList<>();

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

    public boolean isAvailableToHold() {
        // Tá disponível se não tiver sido emprestado ou se não há um empréstimo corrente ;)
        return this.holds.isEmpty() || this.holds.stream().noneMatch(Hold::current);
    }
}
