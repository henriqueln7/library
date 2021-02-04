package com.api.library;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class NewBookInstanceController {

    private final BookRepository bookRepository;

    public NewBookInstanceController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PostMapping("/books/{bookIsbn}/instances")
    public void newBookInstance(@PathVariable("bookIsbn") String bookIsbn, @RequestBody @Valid NewInstanceBookRequest request) {
        Optional<Book> optionalBook = bookRepository.findByIsbn(bookIsbn);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.addBookInstance(request);
            bookRepository.save(book);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with ISBN " + bookIsbn + " NOT FOUND");
        }
    }
}
