package org.book.bookmanager.Borrowed.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BorrowedRequest(
        @NotNull @NotBlank String bookId
) {
}
