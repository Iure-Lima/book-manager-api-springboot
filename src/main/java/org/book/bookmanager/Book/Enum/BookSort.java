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

    public static boolean isValid(String value) {
        for (BookSort sort : BookSort.values()) {
            if (sort.getBookSort().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
