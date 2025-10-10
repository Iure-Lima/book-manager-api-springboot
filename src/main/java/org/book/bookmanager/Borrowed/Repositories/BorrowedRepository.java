package org.book.bookmanager.Borrowed.Repositories;

import org.book.bookmanager.Borrowed.Enum.BorrowedStatus;
import org.book.bookmanager.Borrowed.Model.BorrowedModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BorrowedRepository extends MongoRepository<BorrowedModel, String> {
    Page<BorrowedModel> findAll(Pageable pageable);

    BorrowedModel findByBorrowedId(String borrowedId);

    List<BorrowedModel> findAllByBookIdAndUserLogin(String bookId, String userLogin);
    Page<BorrowedModel> findAllByUserLogin(String userLogin, Pageable pageable);
    Page<BorrowedModel> findAllByBookId(String bookId, Pageable pageable);
    Page<BorrowedModel> findAllByDueAt(LocalDate dueAt, Pageable pageable);
    Page<BorrowedModel> findAllByBorrowedStatus(BorrowedStatus borrowedStatus, Pageable pageable);
    Page<BorrowedModel> findAllByBorrowedStatusAndUserLogin(BorrowedStatus borrowedStatus, String userLogin, Pageable page);

    Page<BorrowedModel> findAllByCheckoutAt(LocalDate checkoutAt, Pageable pageable);
}
