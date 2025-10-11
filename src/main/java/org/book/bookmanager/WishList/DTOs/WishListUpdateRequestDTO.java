package org.book.bookmanager.WishList.DTOs;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record WishListUpdateRequestDTO(
        @NotBlank @NotNull String wishlistName
) {
}
