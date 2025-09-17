package org.book.bookmanager.User.Enum;

public enum UserRole {
    ADMIN("admin"),
    USER("user"),
    LIBRARIAN("librarian");

    private String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole(){
        return this.role;
    }
}
