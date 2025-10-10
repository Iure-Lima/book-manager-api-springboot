package org.book.bookmanager.WishList.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record WishListRequestDTO(
        @NotBlank @NotNull String wishlistName
) { }
