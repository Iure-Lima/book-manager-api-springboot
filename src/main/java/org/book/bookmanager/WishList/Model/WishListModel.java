package org.book.bookmanager.WishList.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "wishlist")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WishListModel {
    @MongoId
    private String wishlistId;

    @Field(name="name")
    private String wishlistName;

    @Field(name = "userlogin")
    @Indexed
    private String userLogin;

    @Field(name="books")
    private List<String> booksId = new ArrayList<>();

    @Field(name="createAt")
    private LocalDateTime createAt;

    @Field(name = "updateAt")
    private LocalDateTime updateAt;

}
