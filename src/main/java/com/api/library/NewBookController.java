package com.api.library;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class NewBookController {

    @PersistenceContext
    private EntityManager manager;

    @PostMapping("/books")
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public void newBook(@RequestBody @Valid NewBookRequest request) {
        Book book = request.newBook();
        manager.persist(book);
    }
}
