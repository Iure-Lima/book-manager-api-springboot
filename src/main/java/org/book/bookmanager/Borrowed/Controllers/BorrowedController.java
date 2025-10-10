package org.book.bookmanager.Borrowed.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.book.bookmanager.Borrowed.DTOs.BorrowedRequest;
import org.book.bookmanager.Borrowed.Enum.BorrowedStatus;
import org.book.bookmanager.Borrowed.Model.BorrowedModel;
import org.book.bookmanager.Borrowed.Services.BorrowedService;
import org.book.bookmanager.User.Services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/borrowed")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name="Borrowed")
public class BorrowedController {

    @Autowired
    BorrowedService borrowedService;

    @Autowired
    TokenService tokenService;

    @Operation(summary = "Request to borrow the book", method = "POST", security = {@SecurityRequirement(name="bearerAuth")} )
    @PostMapping
    public ResponseEntity<BorrowedModel> addBorrowed(@RequestBody @Valid BorrowedRequest borrowedRequest, @RequestHeader(value = "Authorization", required = true) String authorization){
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        String token = authorization.substring(7);
        String email = tokenService.getUserNameOfToken(token);
        if ("Token Invalid or Expired".equals(email)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        BorrowedModel borrowed = this.borrowedService.saveBorrowedModel(borrowedRequest,email);

        if (borrowed == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(borrowed);
    }

    @Operation(summary = "Get all borrow from a user email", method = "GET", security = {@SecurityRequirement(name="bearerAuth")} )
    @GetMapping("/email/{email}")
    public ResponseEntity<Page<BorrowedModel>> getByUserLogin(@PathVariable(value = "email") String email, @PageableDefault(page = 0, size = 10, sort = "createAt", direction = Sort.Direction.ASC) Pageable page){
        return  ResponseEntity.status(HttpStatus.OK).body(this.borrowedService.getByUserLogin(email, page));
    }

    @Operation(summary = "Get all borrow from a me", method = "GET", security = {@SecurityRequirement(name="bearerAuth")} )
    @GetMapping("/me")
    public ResponseEntity<Page<BorrowedModel>> getByMe(@PageableDefault(page = 0, size = 10, sort = "createAt", direction = Sort.Direction.ASC) Pageable page, @RequestHeader(value = "Authorization", required = true) String authorization){
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        String token = authorization.substring(7);
        String email = tokenService.getUserNameOfToken(token);
        if ("Token Invalid or Expired".equals(email)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return  ResponseEntity.status(HttpStatus.OK).body(this.borrowedService.getByUserLogin(email, page));
    }

    @Operation(summary = "Get all borrow from a book id", method = "GET", security = {@SecurityRequirement(name="bearerAuth")} )
    @GetMapping("/book/{id}")
    public ResponseEntity<Page<BorrowedModel>> getByBookId(@PathVariable(value = "id") String id, @PageableDefault(page = 0, size = 10, sort = "createAt", direction = Sort.Direction.ASC) Pageable page){
        return  ResponseEntity.status(HttpStatus.OK).body(this.borrowedService.getByBookId(id, page));
    }

    @Operation(summary = "Get all borrow", method = "GET", security = {@SecurityRequirement(name="bearerAuth")} )
    @GetMapping("/all")
    public ResponseEntity<Page<BorrowedModel>> getAll(@PageableDefault(page = 0, size = 10, sort = "createAt", direction = Sort.Direction.ASC) Pageable page){
        return  ResponseEntity.status(HttpStatus.OK).body(this.borrowedService.getAll(page));
    }

    @Operation(summary = "Get all borrow with Borrowed state", method = "GET", security = {@SecurityRequirement(name="bearerAuth")} )
    @GetMapping("/state/{state}")
    public ResponseEntity<Page<BorrowedModel>> getByState(@PathVariable(value = "state") BorrowedStatus state,  @PageableDefault(page = 0, size = 10, sort = "createAt", direction = Sort.Direction.ASC) Pageable page){
        return  ResponseEntity.status(HttpStatus.OK).body(this.borrowedService.getByState(state, page));
    }

    @Operation(summary = "Get all borrow with Borrowed state and email", method = "GET", security = {@SecurityRequirement(name="bearerAuth")} )
    @GetMapping("/me/state/{state}")
    public ResponseEntity<Page<BorrowedModel>> getByStateAndEmail(@PathVariable(value = "state") BorrowedStatus state,  @PageableDefault(page = 0, size = 10, sort = "createAt", direction = Sort.Direction.ASC) Pageable page, @RequestHeader(value = "Authorization", required = true) String authorization){
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        String token = authorization.substring(7);
        String email = tokenService.getUserNameOfToken(token);
        if ("Token Invalid or Expired".equals(email)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return  ResponseEntity.status(HttpStatus.OK).body(this.borrowedService.getByStateAndEmail(state, email, page));
    }

    @Operation(summary = "Get all borrow with due date", method = "GET", security = {@SecurityRequirement(name="bearerAuth")} )
    @GetMapping("/due/{date}")
    public ResponseEntity<Page<BorrowedModel>> getByDueDate(@PathVariable(value = "date") LocalDate date, @PageableDefault(page = 0, size = 10, sort = "createAt", direction = Sort.Direction.ASC) Pageable page){
        return  ResponseEntity.status(HttpStatus.OK).body(this.borrowedService.getByDueAt(date, page));
    }

    @Operation(summary = "Get all borrow with checkout date", method = "GET", security = {@SecurityRequirement(name="bearerAuth")} )
    @GetMapping("/checkout/{date}")
    public ResponseEntity<Page<BorrowedModel>> getByCheckoutDate(@PathVariable(value = "date") LocalDate date, @PageableDefault(page = 0, size = 10, sort = "createAt", direction = Sort.Direction.ASC) Pageable page){
        return  ResponseEntity.status(HttpStatus.OK).body(this.borrowedService.getByCheckoutAt(date, page));
    }

    @Operation(summary = "Delete borrowed by id", method = "DELETE", security = {@SecurityRequirement(name="bearerAuth")} )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(value = "id") String id, @PageableDefault(page = 0, size = 10, sort = "createAt", direction = Sort.Direction.ASC) Pageable page){
        Object deleted = this.borrowedService.delete(id);

        if (deleted == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This Borrowed dont exist");
        }

        if (!(boolean) deleted) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This Borrowed cannot be deleted");
        }

        return ResponseEntity.status(HttpStatus.OK).body("This Borrowed is deleted");
    }
}
