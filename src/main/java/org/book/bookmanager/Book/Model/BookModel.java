package org.book.bookmanager.Book.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.book.bookmanager.Book.Enum.BookCategory;
import org.book.bookmanager.Book.Enum.BookStatus;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;

@Document(collection = "book")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookModel {

    @MongoId
    private String bookId;

    @Field(name="title")
    private String bookTitle;

    @Field(name = "subtitles")
    private String bookSubtitle;

    @Field(name = "authors")
    private List<String> bookAuthors;

    @Field(name= "publisher")
    private String bookPublisher;

    @Field(name="year_of_publication")
    private Integer bookYear;

    @Field(name= "edition")
    private Integer bookEdition;

    @Field(name= "ISBN")
    private String bookISBN;

    @Field(name="language")
    private String bookLanguage;

    @Field(name="page_number")
    private Integer bookPageNumber;

    @Field(name = "categories")
    private List<BookCategory> bookCategories;

    @Field(name="synopsis")
    private String bookSynopsis;

    @Field(name = "banner_URL")
    private String bookBannerURL;

    @Field(name="quantity_in_stock")
    private Integer bookQuantityInStock;

    @Field(name = "status")
    private BookStatus bookStatus;

    @Field(name = "keywords")
    private List<String> bookKeywords;

    @Field(name="created_at")
    private LocalDateTime createdAt;

    @Field(name="updated_at")
    private LocalDateTime updatedAt;
}
