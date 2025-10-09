package org.book.bookmanager.Borrowed.Enum;

import lombok.Getter;

@Getter
public enum BorrowedStatus {
    WAITING("waiting"),
    APPROVED("approved"),
    FAILED("failed"),
    FINALIZED("finalized");

    private final String status;

    BorrowedStatus(String status) {this.status = status;}

}
