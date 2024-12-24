package com.project.wishlist.repository;

import com.project.wishlist.model.WishList;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface WishListRepository extends MongoRepository<WishList, String> {
    Optional<WishList> findByClientId(String clientId);
    boolean existsByClientIdAndProductsId(String clientId, String productId);
}

