package org.book.bookmanager.Book.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.book.bookmanager.Book.Enum.BookCategory;
import org.book.bookmanager.Book.Enum.BookStatus;

import java.util.List;

public record BookDTORequestCreated(
        @NotNull @NotBlank String bookTitle,
        String bookSubtitle,
        @NotNull @NotBlank String bookAuthor,
        @NotNull @NotBlank String bookPublisher,
        @NotNull Integer bookYear,
        @NotNull Integer bookEdition,
        @NotNull @NotBlank String bookISBN,
        @NotNull @NotBlank String bookLanguage,
        @NotNull Integer bookPageNumber,
        @NotNull List<BookCategory> bookCategories,
        @NotNull @NotBlank String bookSynopsis,
        @NotNull @NotBlank String bookBannerURL,
        @NotNull Integer bookQuantityInStock,
        @NotNull BookStatus bookStatus,
        @NotNull List<String> bookKeywords
) { }
