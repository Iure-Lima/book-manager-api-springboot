package org.book.bookmanager.Borrowed.Enum;

import lombok.Getter;

@Getter
public enum BorrowedStatus {
    WAITING("waiting"),
    OPEN("open"),
    FAILED("failed"),
    OVERDUE("overdue"),
    CANCELLED("cancelled"),
    RETURNED("returned");

    private final String status;

    BorrowedStatus(String status) {this.status = status;}

}
