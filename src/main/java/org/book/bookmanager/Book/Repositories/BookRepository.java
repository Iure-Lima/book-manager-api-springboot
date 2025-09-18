package org.book.bookmanager.Book.Repositories;

import org.book.bookmanager.Book.Enum.BookCategory;
import org.book.bookmanager.Book.Enum.BookStatus;
import org.book.bookmanager.Book.Model.BookModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


import java.util.Collection;
import java.util.List;

@Repository
public interface BookRepository extends MongoRepository<BookModel, String> {
    BookModel findByBookId(String bookId);
    Page<BookModel> findByBookTitle(String bookTitle, Pageable page);
    Page<BookModel> findByBookISBN(String bookISBN, Pageable page);

    Page<BookModel> findAll(Pageable pageable);

    Page<BookModel> findByBookTitleContainingIgnoreCase(String title, Pageable pageable);
    Page<BookModel> findByBookAuthorContainingIgnoreCase(String bookAuthors, Pageable pageable);

    Page<BookModel> findByBookCategoriesIn(Collection<List<BookCategory>> bookCategories, Pageable pageable);
    Page<BookModel> findByBookKeywordsIn(Collection<List<String>> bookKeywords, Pageable pageable);

    Page<BookModel> findByBookYear(Integer bookYear, Pageable pageable);
    Page<BookModel> findByBookLanguage(String bookLanguage, Pageable pageable);
    Page<BookModel> findByBookStatus(BookStatus bookStatus, Pageable pageable);
    Page<BookModel> findByBookPublisher(String bookPublisher, Pageable pageable);

}
