package org.book.bookmanager.Book.Services;

import org.book.bookmanager.Book.DTOs.BookDTORequestCreated;
import org.book.bookmanager.Book.Model.BookModel;
import org.book.bookmanager.Book.Repositories.BookRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
            Pageable page
    ){
        if (title !=null) {
            Page<BookModel> book = this.bookRepository.findByBookTitle(title,page);
            if (!book.getContent().isEmpty()) return book;
            else return this.bookRepository.findByBookTitleContainingIgnoreCase(title, page);
        }

        if (ISBN != null) return  this.bookRepository.findByBookISBN(ISBN, page);;

        return null;
    }
}
