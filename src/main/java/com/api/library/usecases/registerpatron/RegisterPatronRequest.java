package com.api.library.usecases.registerpatron;

import com.api.library.domain.PatronType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class RegisterPatronRequest {
    @NotNull
    public final PatronType type;

    @JsonCreator
    public RegisterPatronRequest(@JsonProperty("type") @NotNull PatronType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "RegisterPatronRequest{" + "type=" + type + '}';
    }
}
