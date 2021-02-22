package com.api.library.usecases.registernewbook;

import com.api.library.domain.Book;
import com.api.library.domain.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Map;

@RestController
public class NewBookController {

    private final BookRepository bookRepository;

    public NewBookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PostMapping("/books")
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> newBook(@RequestBody @Valid NewBookRequest request) {
        boolean bookWithSameIsbnAlreadyExists = bookRepository.findByIsbn(request.isbn).isPresent();

        if (bookWithSameIsbnAlreadyExists) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book with same ISBN already exists");
        }

        Book book = request.newBook();
        bookRepository.save(book);
        return Map.of("id", book.getId(), "title", book.getTitle(), "isbn", book.getIsbn());
    }
}
