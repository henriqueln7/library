package com.api.library.usecases.newhold;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
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
}
