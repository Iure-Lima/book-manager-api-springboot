package org.book.bookmanager.WishList.Services;

import org.book.bookmanager.WishList.DTOs.WishListRequestDTO;
import org.book.bookmanager.WishList.Model.WishListModel;
import org.book.bookmanager.WishList.Repositories.WishListRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class WishListService {

    @Autowired
    WishListRepository wishListRepository;

    public WishListModel createWishList(WishListRequestDTO wishList, String email){
        WishListModel existWishList = this.wishListRepository.findByWishlistName(wishList.wishlistName());

        if (existWishList != null) return existWishList;

        WishListModel newWishList = new WishListModel();
        newWishList.setWishlistId(UUID.randomUUID().toString());
        BeanUtils.copyProperties(wishList, newWishList);
        newWishList.setCreateAt(LocalDateTime.now());
        newWishList.setUpdateAt(LocalDateTime.now());
        newWishList.setUserLogin(email);

        this.wishListRepository.save(newWishList);
        return newWishList;
    }
}
