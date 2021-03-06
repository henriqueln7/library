package com.api.library.usecases.registernewbookinstance;

import com.api.library.domain.Book;
import com.api.library.domain.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
public class NewBookInstanceController {

    private final BookRepository bookRepository;

    public NewBookInstanceController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PostMapping("/books/{bookIsbn}/instances")
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public void newBookInstance(@PathVariable("bookIsbn") String bookIsbn, @RequestBody @Valid NewInstanceBookRequest request) {
        Optional<Book> optionalBook = bookRepository.findByIsbn(bookIsbn);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.addBookInstance(request::newBookInstance);
            bookRepository.save(book);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with ISBN " + bookIsbn + " NOT FOUND");
        }
    }
}
