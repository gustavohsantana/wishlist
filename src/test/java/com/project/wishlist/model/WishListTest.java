package com.project.wishlist.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WishListTest {

    @Test
    void testWishListBuilder() {
        Product product = Product.builder().id("1").name("Produto Teste").build();

        WishList wishList = WishList.builder()
                .clientId("cliente1")
                .products(List.of(product))
                .build();

        assertEquals("cliente1", wishList.getClientId());
        assertEquals(1, wishList.getProducts().size());
        assertEquals("1", wishList.getProducts().get(0).getId());
    }

    @Test
    void testWishListEqualsAndHashCode() {
        Product product = Product.builder().id("1").name("Produto Teste").build();

        List<Product> productList1 = new ArrayList<>();
        productList1.add(product);

        WishList wishList1 = WishList.builder().clientId("cliente1").products(productList1).build();

        List<Product> productList2 = new ArrayList<>();
        productList2.add(product);

        WishList wishList2 = WishList.builder().clientId("cliente1").products(productList2).build();

        assertEquals(wishList1, wishList2);
        assertEquals(wishList1.hashCode(), wishList2.hashCode());

        wishList2.getProducts().clear();

        assertNotEquals(wishList1, wishList2);
    }

    @Test
    void testWishListToString() {
        Product product = Product.builder().id("1").name("Produto Teste").build();
        WishList wishList = WishList.builder().clientId("cliente1").products(List.of(product)).build();

        String expectedToString = "WishList(id=null, clientId=cliente1, products=[Product(id=1, name=Produto Teste)])";
        assertEquals(expectedToString, wishList.toString());
    }
}
