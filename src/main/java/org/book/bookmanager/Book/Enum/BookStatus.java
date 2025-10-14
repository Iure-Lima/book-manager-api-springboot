package org.book.bookmanager.Book.Enum;

import lombok.Getter;

@Getter
public enum BookStatus {
    AVAILABLE("available"),
    UNAVAILABLE("unavailable");

    private final String status;

    BookStatus(String status){
        this.status = status;
    }
}
