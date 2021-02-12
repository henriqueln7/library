package com.api.library.domain;

import org.springframework.util.Assert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Patron {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private PatronType type;

    @Deprecated
    protected Patron(){}

    public Patron(@NotNull PatronType type) {
        this.type = type;
    }

    public Long getId() {
        Assert.notNull(this.id, "ID is null. Perhaps this entity was not persisted");
        return this.id;
    }

    public PatronType getType() {
        return this.type;
    }

    public boolean researcher() {
        return this.type.equals(PatronType.RESEARCHER);
    }
}
