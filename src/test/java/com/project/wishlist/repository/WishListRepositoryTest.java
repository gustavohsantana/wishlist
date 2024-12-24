package com.project.wishlist.repository;

import com.project.wishlist.model.Product;
import com.project.wishlist.model.WishList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class WishListRepositoryTest {

    @Autowired
    private WishListRepository wishListRepository;

    @BeforeEach
    void cleanDatabase() {
        wishListRepository.deleteAll();
    }

    @Test
    void shouldSaveAndFindWishListByClientId() {
        WishList wishList = WishList.builder()
                .clientId("clientABC1")
                .products(List.of(Product.builder().id("1").name("Produto Teste").build()))
                .build();

        wishListRepository.save(wishList);

        Optional<WishList> retrievedWishList = wishListRepository.findByClientId("clientABC1");

        assertTrue(retrievedWishList.isPresent());
        assertEquals("clientABC1", retrievedWishList.get().getClientId());
        assertEquals(1, retrievedWishList.get().getProducts().size());
    }

    @Test
    void shouldReturnEmptyWhenClientIdDoesNotExist() {
        Optional<WishList> retrievedWishList = wishListRepository.findByClientId("inexistente");

        assertFalse(retrievedWishList.isPresent());
    }

    @Test
    void shouldDeleteWishListById() {
        WishList wishList = WishList.builder()
                .clientId("clientABC1")
                .products(List.of(Product.builder().id("1").name("Produto Teste").build()))
                .build();

        wishList = wishListRepository.save(wishList);

        wishListRepository.deleteById(wishList.getId());

        Optional<WishList> retrievedWishList = wishListRepository.findById(wishList.getId());
        assertFalse(retrievedWishList.isPresent());
    }
}
