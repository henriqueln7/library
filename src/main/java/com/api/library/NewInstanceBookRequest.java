package com.api.library;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class NewInstanceBookRequest {

    @NotNull
    private final CirculationType circulationType;

    @JsonCreator
    public NewInstanceBookRequest(@JsonProperty("circulationType") @NotNull CirculationType circulationType) {
        this.circulationType = circulationType;
    }

    @Override
    public String toString() {
        return "NewInstanceBookRequest{" + "circulationType=" + circulationType + '}';
    }

    public BookInstance newBookInstance(Book book) {
        return new BookInstance(book, this.circulationType);
    }
}
