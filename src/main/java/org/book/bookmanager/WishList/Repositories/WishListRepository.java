package org.book.bookmanager.WishList.Repositories;

import org.book.bookmanager.WishList.Model.WishListModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishListRepository extends MongoRepository<WishListModel, String> {
    WishListModel findByWishlistId(String wishlistId);
    Page<WishListModel> findAllByUserLogin(String userLogin, Pageable pageable);
}
