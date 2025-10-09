package org.book.bookmanager.Borrowed.Services;

import org.book.bookmanager.Book.DTOs.BookDTORequestUpdated;
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
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BorrowedService {

    @Autowired
    BorrowedRepository borrowedRepository;

    @Autowired
    BookService bookService;

    public BorrowedModel saveBorrowedModel(BorrowedRequest borrowedRequest, String email){
        BookModel book = this.bookService.getBookById(borrowedRequest.bookId());
        List<BorrowedModel> existBorrowedForBook =
                borrowedRepository.findAllByBookIdAndUserLogin(borrowedRequest.bookId(), email)
                        .stream()
                        .filter(b -> EnumSet.of(BorrowedStatus.WAITING, BorrowedStatus.OPEN, BorrowedStatus.OVERDUE)
                                .contains(b.getBorrowedStatus()))
                        .toList();

        if (!existBorrowedForBook.isEmpty() || book == null) return null;

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

        book.setBookQuantityInStock(book.getBookQuantityInStock() - 1);
        this.bookService.updateBook(book);
        this.borrowedRepository.save(newBorrowed);
        return newBorrowed;

    }
}
