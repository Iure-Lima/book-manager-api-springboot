package org.book.bookmanager.Book.Services;

import org.book.bookmanager.Book.DTOs.BookDTORequestCreated;
import org.book.bookmanager.Book.Model.BookModel;
import org.book.bookmanager.Book.Repositories.BookRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    public BookModel saveBook(BookDTORequestCreated bookDTORequestCreated){
        BookModel newBook = new BookModel();
        newBook.setBookId(UUID.randomUUID().toString());
        BeanUtils.copyProperties(bookDTORequestCreated, newBook);

        bookRepository.save(newBook);
        return newBook;
    }
}
