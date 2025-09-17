package org.book.bookmanager.Book.Repositories;

import org.book.bookmanager.Book.Enum.BookCategory;
import org.book.bookmanager.Book.Enum.BookStatus;
import org.book.bookmanager.Book.Model.BookModel;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface BookRepository extends MongoRepository<BookModel, String> {
    BookModel findByBookId(String bookId);
    BookModel findByBookTitle(String bookTitle);
    BookModel findByBookISBN(String bookISBN);

    Page<BookModel> findByBookCategories(List<BookCategory> bookCategories, Pageable pageable);
    Page<BookModel> findByBookKeywords(List<String> bookKeywords, Pageable pageable);
    Page<BookModel> findByBookAuthors(String bookAuthors, Pageable pageable);
    Page<BookModel> findAll(Pageable pageable);
    Page<BookModel> findByBookYear(Integer bookYear, Pageable pageable);
    Page<BookModel> findByBookLanguage(String bookLanguage, Pageable pageable);
    Page<BookModel> findByBookStatus(BookStatus bookStatus, Pageable pageable);
    Page<BookModel> findByBookPublisher(String bookPublisher, Pageable pageable);

}
