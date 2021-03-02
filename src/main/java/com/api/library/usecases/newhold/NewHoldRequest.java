package com.api.library.usecases.newhold;

import com.api.library.domain.BookInstance;
import com.api.library.domain.Hold;
import com.api.library.domain.Patron;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Objects;
import java.util.Optional;

public class NewHoldRequest {
    @NotNull
    private final Long patronId;
    @NotNull
    private final Long bookInstanceId;
    @Positive
    @Max(60)
    private final Integer daysHold;

    public NewHoldRequest(@NotNull Long patronId, @NotNull Long bookInstanceId, @Positive @Max(60) Integer daysHold) {
        this.patronId = patronId;
        this.bookInstanceId = bookInstanceId;
        this.daysHold = daysHold;
    }

    @Override
    public String toString() {
        return "NewHoldRequest{" + "patronId=" + patronId + ", bookId=" + bookInstanceId + ", daysHold=" + daysHold + '}';
    }

    public Long getPatronId() {
        return patronId;
    }

    public Long getBookInstanceId() {
        return bookInstanceId;
    }

    public Optional<Integer> getDaysHold() {
        return Optional.ofNullable(daysHold);
    }

    public Hold newHold(EntityManager manager) {
        Patron patron = manager.find(Patron.class, this.patronId);
        BookInstance bookInstance = manager.find(BookInstance.class, this.bookInstanceId);

        Assert.state(Objects.nonNull(patron), "Patron does not exist");
        Assert.state(Objects.nonNull(bookInstance), "BookInstance does not exist");

        int maxAmountDaysHold = 60;
        Integer amountOfDaysHold = Optional.ofNullable(this.daysHold).orElse(maxAmountDaysHold);

        return new Hold(patron, bookInstance, amountOfDaysHold);
    }
}
