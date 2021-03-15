package com.api.library.usecases.newhold;

import com.api.library.domain.BookInstance;
import com.api.library.domain.Patron;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Objects;

@Component
public class NewHoldValidator implements Validator {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public boolean supports(Class<?> clazz) {
        return NewHoldRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        NewHoldRequest request = (NewHoldRequest) target;

        Patron patron = manager.find(Patron.class, request.getPatronId());
        BookInstance bookInstance = manager.find(BookInstance.class, request.getBookInstanceId());

        Assert.state(Objects.nonNull(patron), "Patron does not exist");
        Assert.state(Objects.nonNull(bookInstance), "BookInstance does not exist");

        if (!bookInstance.acceptToBeHoldTo(patron)) {
            errors.reject(null, "This book instance cannot be hold to this patron");
        }

        if (request.getDaysHold().isEmpty() && !patron.researcher()) {
            errors.rejectValue("daysHold", null, "You need to pass a daysHold attribute");
        }

        if (!bookInstance.isAvailableToHold(patron)) {
            errors.reject(null, "Book instance not available to be hold");
        }
    }
}
