package org.book.bookmanager.Book.Services;

import org.book.bookmanager.Book.DTOs.BookDTORequestCreated;
import org.book.bookmanager.Book.DTOs.BookDTORequestUpdated;
import org.book.bookmanager.Book.Enum.BookStatus;
import org.book.bookmanager.Book.Model.BookModel;
import org.book.bookmanager.Book.Repositories.BookRepository;
import org.book.bookmanager.User.Services.TokenService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    public BookModel saveBook(BookDTORequestCreated bookDTORequestCreated){
        BookModel newBook = new BookModel();
        newBook.setBookId(UUID.randomUUID().toString());
        BeanUtils.copyProperties(bookDTORequestCreated, newBook);
        newBook.setCreatedAt(LocalDateTime.now());
        newBook.setUpdatedAt(LocalDateTime.now());
        newBook.setBookReviews(0.0);
        newBook.setBookPopularity(0.0);
        System.out.printf(String.valueOf(LocalDateTime.now()));

        bookRepository.save(newBook);
        return newBook;
    }

    public void removeBook(BookModel book){
        this.bookRepository.delete(book);
    }

    public BookModel getBookById(String id){
        return this.bookRepository.findByBookId(id);
    }

    public Page<BookModel> search(
            String title,
            String ISBN,
            String author,
            Integer year,
            String language,
            BookStatus status,
            String publisher,
            List<String> categories,
            List<String> keywords,
            Pageable page
    ){
        if (title !=null) {
            Page<BookModel> book = this.bookRepository.findByBookTitle(title,page);
            if (!book.getContent().isEmpty()) return book;
            else return this.bookRepository.findByBookTitleContainingIgnoreCase(title, page);
        }

        if (ISBN != null) return  this.bookRepository.findByBookISBN(ISBN, page);

        if (author != null) return this.bookRepository.findByBookAuthorContainingIgnoreCase(author,page);

        if (year != null) return this.bookRepository.findByBookYear(year, page);

        if (language != null) return this.bookRepository.findByBookLanguage(language, page);

        if (status != null) return this.bookRepository.findByBookStatus(status, page);

        if (publisher != null) return this.bookRepository.findByBookPublisher(publisher, page);

        if (categories != null && !categories.isEmpty()) return this.bookRepository.findByBookCategoriesIn(categories, page);

        if (keywords != null && !keywords.isEmpty()) return this.bookRepository.findByBookKeywordsIn(keywords, page);

        return this.bookRepository.findAll(page);
    }

    public BookModel updateBook(BookDTORequestUpdated dto, String id){
        BookModel newBook = bookRepository.findByBookId(id);

        Optional.ofNullable(dto.bookTitle()).ifPresent(newBook::setBookTitle);
        Optional.ofNullable(dto.bookSubtitle()).ifPresent(newBook::setBookSubtitle);
        Optional.ofNullable(dto.bookAuthor()).ifPresent(newBook::setBookAuthor);
        Optional.ofNullable(dto.bookPublisher()).ifPresent(newBook::setBookPublisher);
        Optional.ofNullable(dto.bookYear()).ifPresent(newBook::setBookYear);
        Optional.ofNullable(dto.bookEdition()).ifPresent(newBook::setBookEdition);
        Optional.ofNullable(dto.bookLanguage()).ifPresent(newBook::setBookLanguage);
        Optional.ofNullable(dto.bookPageNumber()).ifPresent(newBook::setBookPageNumber);
        Optional.ofNullable(dto.bookCategories()).ifPresent(newBook::setBookCategories);
        Optional.ofNullable(dto.bookSynopsis()).ifPresent(newBook::setBookSynopsis);
        Optional.ofNullable(dto.bookBannerURL()).ifPresent(newBook::setBookBannerURL);
        Optional.ofNullable(dto.bookQuantityInStock()).ifPresent(newBook::setBookQuantityInStock);
        Optional.ofNullable(dto.bookStatus()).ifPresent(newBook::setBookStatus);
        Optional.ofNullable(dto.bookKeywords()).ifPresent(newBook::setBookKeywords);
        Optional.ofNullable(dto.bookPopularity()).ifPresent(newBook::setBookPopularity);
        Optional.ofNullable(dto.bookReviews()).ifPresent(newBook::setBookReviews);
        newBook.setUpdatedAt(LocalDateTime.now());

        if (newBook.getBookQuantityInStock() == 0) newBook.setBookStatus(BookStatus.UNAVAILABLE);
        bookRepository.save(newBook);
        return newBook;
    }


}
