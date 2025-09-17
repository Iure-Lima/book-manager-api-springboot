package org.book.bookmanager.Book.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@Tag(name="Book")
public class BookController {
    @Autowired
    BookService bookService;

    @Operation(summary = "Adding books from the database", method = "POST",security = { @SecurityRequirement(name = "bearerAuth") })
    @PostMapping
    public ResponseEntity<BookModel> addProduct(@RequestBody @Valid BookDTORequestCreated bookDTORequestCreated){
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.saveBook(bookDTORequestCreated));
    }
}
