package org.book.bookmanager.Borrowed.Services;

import org.book.bookmanager.Book.Enum.BookStatus;
import org.book.bookmanager.Book.Model.BookModel;
import org.book.bookmanager.Book.Services.BookService;
import org.book.bookmanager.Borrowed.DTOs.BorrowedRequest;
import org.book.bookmanager.Borrowed.Enum.BorrowedStatus;
import org.book.bookmanager.Borrowed.Model.BorrowedModel;
import org.book.bookmanager.Borrowed.Repositories.BorrowedRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class BorrowedService {

    @Autowired
    BorrowedRepository borrowedRepository;

    @Autowired
    BookService bookService;

    private BorrowedModel saveBorrowedModel(BorrowedRequest borrowedRequest, String email){
        BookModel book = this.bookService.getBookById(borrowedRequest.bookId());
        if (book == null) return null;

        if (book.getBookStatus() == BookStatus.UNAVAILABLE || book.getBookQuantityInStock() <= 0) return null;

        BorrowedModel newBorrowed = new BorrowedModel();
        newBorrowed.setBorrowedId(UUID.randomUUID().toString());
        BeanUtils.copyProperties(borrowedRequest, newBorrowed);
        newBorrowed.setBorrowedStatus(BorrowedStatus.WAITING);
        newBorrowed.setUserLogin(email);
        newBorrowed.setCheckoutAt(LocalDateTime.now());
        newBorrowed.setUpdatedAt(LocalDateTime.now());
        newBorrowed.setCreatedAt(LocalDateTime.now());
        newBorrowed.setDueAt(LocalDateTime.now().plusDays(30));

        this.borrowedRepository.save(newBorrowed);
        return newBorrowed;

    }
}
