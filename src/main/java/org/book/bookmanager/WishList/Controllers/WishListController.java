package org.book.bookmanager.WishList.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.book.bookmanager.Book.Services.BookService;
import org.book.bookmanager.User.Services.TokenService;
import org.book.bookmanager.WishList.DTOs.WishListAddBookRequestDTO;
import org.book.bookmanager.WishList.DTOs.WishListRequestDTO;
import org.book.bookmanager.WishList.DTOs.WishListUpdateRequestDTO;
import org.book.bookmanager.WishList.Model.WishListModel;
import org.book.bookmanager.WishList.Services.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    @Operation(summary = "Request to create wishlist", method = "POST", security = {@SecurityRequirement(name="bearerAuth")})
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

        return this.wishListService.createWishList(wishListRequestDTO, email);
    }

    @Operation(summary = "Delete wish list by id", method = "DELETE", security = {@SecurityRequirement(name="bearerAuth")})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWishList(@PathVariable(name = "id") String id, @RequestHeader(value = "Authorization", required = true) String authorization){
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        String token = authorization.substring(7);
        String email = tokenService.getUserNameOfToken(token);
        if ("Token Invalid or Expired".equals(email)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        boolean deletedWishList = this.wishListService.deleteWishList(id, email);

        if (!deletedWishList) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Wish List not found");

        return ResponseEntity.status(HttpStatus.OK).body("Wish list deleted");
    }

    @Operation(summary = "Add book in the wish list", method = "POST", security = {@SecurityRequirement(name="bearerAuth")})
    @PostMapping("/addBook/{id}")
    public ResponseEntity<WishListModel> addBookInWishList(@PathVariable(name = "id") String id, @RequestBody @Valid WishListAddBookRequestDTO bookId, @RequestHeader(value = "Authorization", required = true) String authorization){
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        String token = authorization.substring(7);
        String email = tokenService.getUserNameOfToken(token);
        if ("Token Invalid or Expired".equals(email)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return  this.wishListService.addBook(id, bookId, email);
    }

    @Operation(summary = "Remove book in the wish list", method = "DELETE", security = {@SecurityRequirement(name="bearerAuth")})
    @DeleteMapping("/removeBook/{id}")
    public ResponseEntity<WishListModel> removeBookInWishList(@PathVariable(name = "id") String id, @RequestBody @Valid WishListAddBookRequestDTO bookId, @RequestHeader(value = "Authorization", required = true) String authorization){
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        String token = authorization.substring(7);
        String email = tokenService.getUserNameOfToken(token);
        if ("Token Invalid or Expired".equals(email)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return  this.wishListService.removeBook(id, bookId, email);
    }

    @Operation(summary = "Get wish list by name", method = "GET", security = {@SecurityRequirement(name="bearerAuth")})
    @GetMapping("/wishName")
    public ResponseEntity<WishListModel> getWishListByName(@RequestParam(required = true) String name, @RequestHeader(value = "Authorization", required = true) String authorization){
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        String token = authorization.substring(7);
        String email = tokenService.getUserNameOfToken(token);
        if ("Token Invalid or Expired".equals(email)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }


        return this.wishListService.getWishListByName(name, email);
    }

    @Operation(summary = "Get wish list by id", method = "GET", security = {@SecurityRequirement(name="bearerAuth")})
    @GetMapping("/{id}")
    public ResponseEntity<WishListModel> getWishListById(@PathVariable(value = "id") String id, @RequestHeader(value = "Authorization", required = true) String authorization){
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        String token = authorization.substring(7);
        String email = tokenService.getUserNameOfToken(token);
        if ("Token Invalid or Expired".equals(email)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        return this.wishListService.getWishListById(id, email);
    }

    @Operation(summary = "Get all wish list", method = "GET", security = {@SecurityRequirement(name="bearerAuth")})
    @GetMapping()
    public ResponseEntity<Page<WishListModel>> getAllWishList(@PageableDefault(size = 10, page = 0, sort = "name", direction = Sort.Direction.ASC)Pageable page, @RequestHeader(value = "Authorization", required = true) String authorization){
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        String token = authorization.substring(7);
        String email = tokenService.getUserNameOfToken(token);
        if ("Token Invalid or Expired".equals(email)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        return this.wishListService.getAllWishList(email, page);
    }

    @Operation(summary = "Update wish list", method = "PUT", security = {@SecurityRequirement(name="bearerAuth")})
    @PutMapping("/{id}")
    public ResponseEntity<WishListModel> updateWishList(@RequestBody @Valid WishListUpdateRequestDTO dto, @PathVariable(value = "id") String id, @RequestHeader(value = "Authorization", required = true) String authorization){
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        String token = authorization.substring(7);
        String email = tokenService.getUserNameOfToken(token);
        if ("Token Invalid or Expired".equals(email)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        return this.wishListService.updateWishListName(id,dto, email);
    }
}
