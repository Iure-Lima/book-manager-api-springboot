package org.book.bookmanager.Book.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.book.bookmanager.Book.DTOs.BookDTORequestCreated;
import org.book.bookmanager.Book.Model.BookModel;
import org.book.bookmanager.Book.Services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name="book-controller")
public class BookController {
    @Autowired
    BookService bookService;

    @PostMapping
    @Operation(summary = "Adding books from the database", method = "POST")
    public ResponseEntity<BookModel> addProduct(@Valid @RequestBody BookDTORequestCreated bookDTORequestCreated){
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.saveBook(bookDTORequestCreated));
    }
}
