package org.book.bookmanager.Book.Enum;

import lombok.Getter;

@Getter
public enum BookStatus {
    AVALIABLE("available"),
    UNAVAILABLE("unavailable");

    private final String status;

    BookStatus(String status){
        this.status = status;
    }
}
