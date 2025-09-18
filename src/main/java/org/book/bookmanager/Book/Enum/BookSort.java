package org.book.bookmanager.Book.Enum;

import lombok.Getter;

@Getter
public enum BookSort {
    BOOK_TITLE("bookTitle"),
    BOOK_AUTHOR("bookAuthor"),
    BOOK_YEAR("bookYear"),
    BOOK_POPULARITY("bookPopularity");

    private final String bookSort;

    BookSort(String bookSort){
        this.bookSort = bookSort;
    }
}
