package org.book.bookmanager.Book.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.book.bookmanager.Book.DTOs.BookDTORequestCreated;
import org.book.bookmanager.Book.Enum.BookSort;
import org.book.bookmanager.Book.Model.BookModel;
import org.book.bookmanager.Book.Services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @Operation(summary = "Remove book from the database", method = "DELETE",security = { @SecurityRequirement(name = "bearerAuth") })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable(value = "id") String id){
        BookModel book = this.bookService.getBookById(id);
        if (book == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
        this.bookService.removeBook(book);
        return ResponseEntity.status(HttpStatus.OK).body("Book deleted");
    }

    @Operation(summary = "Get book by id", method = "GET")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getBookById(@PathVariable(value = "id") String id){
        BookModel book = this.bookService.getBookById(id);
        if (book == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    @Operation(summary = "Route to research books", method = "GET")
    @GetMapping("/search")
    public ResponseEntity<Object> search(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "BOOK_TITLE") BookSort sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction,

            //Search filters
            @RequestParam(required = false) String title

    ){
        if (!BookSort.isValid(sortBy.getBookSort())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid sort parameter");
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy.getBookSort()));
        return ResponseEntity.status(HttpStatus.OK).body(this.bookService.search(title,pageable));

    }
}
