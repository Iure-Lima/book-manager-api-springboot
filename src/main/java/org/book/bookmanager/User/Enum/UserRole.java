package org.book.bookmanager.User.Enum;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("admin"),
    USER("user"),
    LIBRARIAN("librarian");

    private final String role;

    UserRole(String role){
        this.role = role;
    }
}
