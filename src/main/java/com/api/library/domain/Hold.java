package com.api.library.domain;

import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.Optional;

@Entity
public class Hold {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull @Valid
    @ManyToOne
    private Patron patron;
    @NotNull @Valid
    @ManyToOne
    private BookInstance bookInstance;
    @Positive @Max(60)
    private Integer daysHold;
    private LocalDate createdAt;
    private LocalDate returnedAt;

    @Deprecated
    protected Hold(){}

    public Hold(@NotNull @Valid Patron patron, @NotNull @Valid BookInstance bookInstance, @Positive @Max(60) Integer daysHold) {
        Assert.isTrue(bookInstance.acceptToBeHoldTo(patron), "This book instance cannot be hold to this patron");
        this.patron = patron;
        this.bookInstance = bookInstance;
        this.daysHold = daysHold;
        this.createdAt = LocalDate.now();
    }

    @Override
    public String toString() {
        return "Hold{" + "id=" + id + ", patron=" + patron + ", bookInstance=" + bookInstance + ", daysHold=" + daysHold + ", createdAt=" + createdAt + '}';
    }

    public boolean current() {
        // Empréstimo corrente: não foi devolvido
        return Optional.ofNullable(this.returnedAt).isPresent();
    }
}
