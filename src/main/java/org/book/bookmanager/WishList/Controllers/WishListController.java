package org.book.bookmanager.WishList.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.book.bookmanager.Book.Services.BookService;
import org.book.bookmanager.User.Services.TokenService;
import org.book.bookmanager.WishList.DTOs.WishListRequestDTO;
import org.book.bookmanager.WishList.Model.WishListModel;
import org.book.bookmanager.WishList.Services.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/wishlist")
@Tag(name = "WishList")
public class WishListController {

    @Autowired
    WishListService wishListService;

    @Autowired
    TokenService tokenService;

    @Autowired
    BookService bookService;

    @Operation(summary = "Requesto to create wishlist", method = "POST", security = {@SecurityRequirement(name="bearerAuth")})
    @PostMapping
    public ResponseEntity<WishListModel> createWishList(@RequestBody @Valid WishListRequestDTO wishListRequestDTO, @RequestHeader(value = "Authorization", required = true) String authorization){
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        String token = authorization.substring(7);
        String email = tokenService.getUserNameOfToken(token);
        if ("Token Invalid or Expired".equals(email)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(this.wishListService.createWishList(wishListRequestDTO, email));
    }
}
