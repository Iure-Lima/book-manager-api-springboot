package org.book.bookmanager.Borrowed.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.book.bookmanager.Borrowed.Enum.BorrowedStatus;

public record BorrowedUpdateStatusRequest(
        @NotNull @NotBlank BorrowedStatus borrowedStatus
) {
}
