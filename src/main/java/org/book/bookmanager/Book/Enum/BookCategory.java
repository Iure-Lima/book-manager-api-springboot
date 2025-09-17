package org.book.bookmanager.Book.Enum;

import lombok.Getter;

@Getter
public enum BookCategory {
    ROMANCE("romance"),
    FANTASY("fantasy"),
    SCIENCE_FICTION("science fiction"),
    MYSTERY("mystery"),
    THRILLER("thriller"),
    HORROR("horror"),
    DRAMA("drama"),
    POETRY("poetry"),
    YOUNG_ADULT("young adult"),
    CHILDREN("children"),
    GRAPHIC_NOVEL("graphic novel"),
    MANGA("manga"),

    NON_FICTION("non-fiction"),
    BIOGRAPHY("biography"),
    HISTORY("history"),
    RELIGION("religion"),
    SELF_HELP("self-help"),
    ACADEMIC("academic"),
    EDUCATION("education"),
    LAW("law"),
    POLITICS("politics"),
    PHILOSOPHY("philosophy"),
    SCIENCE("science"),
    TECHNOLOGY("technology"),
    BUSINESS("business"),
    ECONOMY("economy"),
    HEALTH("health"),
    FITNESS("fitness"),
    COOKBOOK("cookbook"),
    ART("art"),
    PHOTOGRAPHY("photography"),
    TRAVEL("travel");

    private final String category;

    BookCategory(String category){
        this.category = category;
    }

}
