package org.book.bookmanager.Borrowed.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.book.bookmanager.Borrowed.Enum.BorrowedStatus;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "borrowed")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BorrowedModel {

    @MongoId
    private String borrowedId;

    @Field(name="user_login")
    @Indexed
    private String userLogin;

    @Field(name="bookId")
    @Indexed
    private String bookId;

    @Field(name = "borrowed_status")
    private BorrowedStatus borrowedStatus;

    @Field(name="checkoutAt")
    private LocalDate checkoutAt;

    @Field(name="dueAt")
    private LocalDate dueAt;

    @Field(name="createdAt")
    private LocalDateTime createdAt;

    @Field(name="updatedAt")
    private LocalDateTime updatedAt;

}
