package org.book.bookmanager.Borrowed.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.book.bookmanager.Borrowed.DTOs.BorrowedRequest;
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

    @Operation(summary = "Take all borrow from a user email", method = "GET", security = {@SecurityRequirement(name="bearerAuth")} )
    @GetMapping("/email/{email}")
    public ResponseEntity<Page<BorrowedModel>> getByUserLogin(@PathVariable(value = "email") String email, @PageableDefault(page = 0, size = 10, sort = "createAt", direction = Sort.Direction.ASC) Pageable page){
        return  ResponseEntity.status(HttpStatus.OK).body(this.borrowedService.getByUserLogin(email, page));
    }

    @Operation(summary = "Take all borrow from a me", method = "GET", security = {@SecurityRequirement(name="bearerAuth")} )
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
}
