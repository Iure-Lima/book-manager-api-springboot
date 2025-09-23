package org.book.bookmanager.User.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.book.bookmanager.User.Enum.UserRole;

public record RegisterDTO(
        @NotNull @NotNull String email,
        @NotNull @NotBlank String password,
        @NotNull @NotBlank String username,
        @NotNull UserRole role
        ) {}
