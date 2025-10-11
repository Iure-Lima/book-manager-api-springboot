package org.book.bookmanager.WishList.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record WishListAddBookRequestDTO(
        @NotNull @NotBlank String bookId
) {
}
