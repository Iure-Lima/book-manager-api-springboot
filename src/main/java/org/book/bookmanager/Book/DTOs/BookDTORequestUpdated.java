package org.book.bookmanager.Book.DTOs;

import org.book.bookmanager.Book.Enum.BookCategory;
import org.book.bookmanager.Book.Enum.BookStatus;

import java.util.List;

public record BookDTORequestUpdated(
        String bookTitle,
        String bookSubtitle,
        String bookAuthor,
        String bookPublisher,
        Integer bookYear,
        Integer bookEdition,
        String bookLanguage,
        Integer bookPageNumber,
        List<BookCategory> bookCategories,
        String bookSynopsis,
        String bookBannerURL,
        Integer bookQuantityInStock,
        BookStatus bookStatus,
        List<String> bookKeywords,
        Double bookPopularity,
        Double bookReviews
) { }
