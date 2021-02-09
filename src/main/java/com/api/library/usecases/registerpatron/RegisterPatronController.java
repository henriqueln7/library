package com.api.library.usecases.registerpatron;

import com.api.library.domain.Patron;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Map;

@RestController
public class RegisterPatronController {

    @PersistenceContext
    private EntityManager manager;

    @PostMapping("/patrons")
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> registerUser(@RequestBody @Valid RegisterPatronRequest request) {
        Patron patron = new Patron(request.type);
        manager.persist(patron);
        return Map.of("id", patron.getId(), "type", patron.getType());
    }

}
