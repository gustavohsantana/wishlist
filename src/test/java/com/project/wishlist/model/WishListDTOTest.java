package com.project.wishlist.model;

import com.project.wishlist.dto.WishListDTO;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class WishListDTOTest {

    @Test
    void shouldCreateWishListDTO() {
        WishListDTO wishListDTO = WishListDTO.builder()
                .id("1")
                .clientId("client1")
                .products(Collections.emptyList())
                .build();

        assertNotNull(wishListDTO);
        assertEquals("1", wishListDTO.getId());
        assertEquals("client1", wishListDTO.getClientId());
        assertTrue(wishListDTO.getProducts().isEmpty());
    }

    @Test
    void shouldSetAndGetId() {
        WishListDTO wishListDTO = WishListDTO.builder().build();
        wishListDTO.setId("1");

        assertEquals("1", wishListDTO.getId());
    }

    @Test
    void shouldSetAndGetClientId() {
        WishListDTO wishListDTO = WishListDTO.builder().build();
        wishListDTO.setClientId("client1");

        assertEquals("client1", wishListDTO.getClientId());
    }

    @Test
    void shouldSetAndGetProducts() {
        WishListDTO wishListDTO = WishListDTO.builder().build();
        wishListDTO.setProducts(Collections.emptyList());

        assertTrue(wishListDTO.getProducts().isEmpty());
    }
}