package com.api.library.domain;

import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Patron {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private PatronType type;
    @OneToMany(mappedBy = "patron")
    private final List<Hold> holds = new ArrayList<>();

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

    public boolean allowedToHold(BookInstance bookInstance) {
        boolean userAlreadyHasInstanceOfBook = holds.stream()
                                                    .filter(Hold::current)
                                                    .map(Hold::getBookInstance)
                                                    .anyMatch(instance -> instance.isInstanceOfSameBookAs(bookInstance));

        long amountOfCurrentHolds = this.holds.stream().filter(Hold::current).count();
        return this.type.allowedToHold(amountOfCurrentHolds) && !userAlreadyHasInstanceOfBook;
    }
}
