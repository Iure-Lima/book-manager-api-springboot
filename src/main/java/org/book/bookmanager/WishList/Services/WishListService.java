package org.book.bookmanager.WishList.Services;

import org.book.bookmanager.Book.Model.BookModel;
import org.book.bookmanager.Book.Services.BookService;
import org.book.bookmanager.WishList.DTOs.WishListAddBookRequestDTO;
import org.book.bookmanager.WishList.DTOs.WishListRequestDTO;
import org.book.bookmanager.WishList.Model.WishListModel;
import org.book.bookmanager.WishList.Repositories.WishListRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class WishListService {

    @Autowired
    WishListRepository wishListRepository;

    @Autowired
    BookService bookService;

    public WishListModel createWishList(WishListRequestDTO wishList, String email){
        WishListModel existWishList = this.wishListRepository.findByWishlistName(wishList.wishlistName());

        if (existWishList != null) return existWishList;

        WishListModel newWishList = new WishListModel();
        newWishList.setWishlistId(UUID.randomUUID().toString());
        BeanUtils.copyProperties(wishList, newWishList);
        newWishList.setCreateAt(LocalDateTime.now());
        newWishList.setUpdateAt(LocalDateTime.now());
        newWishList.setUserLogin(email);

        this.wishListRepository.save(newWishList);
        return newWishList;
    }

    public boolean deleteWishList(String id, String email){
        WishListModel wishList = this.wishListRepository.findByWishlistId(id);

        if (wishList == null) return false;

        if (!wishList.getUserLogin().equals(email)) return false;

        this.wishListRepository.delete(wishList);
        return true;
    }

    public ResponseEntity<WishListModel> addBook(String id, WishListAddBookRequestDTO bookIdDTO, String email){
        String bookId = bookIdDTO.bookId();
        WishListModel wishList = this.wishListRepository.findByWishlistId(id);

        if (wishList == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        if (!wishList.getUserLogin().equals(email)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        List<String> books = wishList.getBooksId();
        if (books == null) {
            books = new ArrayList<>();
            wishList.setBooksId(books);
        }

        if (books.contains(bookId)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(wishList);
        }


        BookModel book = this.bookService.getBookById(bookId);
        if (book == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        books.add(bookId);
        wishList.setUpdateAt(LocalDateTime.now());
        this.wishListRepository.save(wishList);

        return ResponseEntity.ok(wishList);
    }
}
