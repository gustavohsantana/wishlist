package com.project.wishlist.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void testProductBuilder() {
        Product product = Product.builder()
                .id("1")
                .name("Produto Teste")
                .build();

        assertEquals("1", product.getId());
        assertEquals("Produto Teste", product.getName());
    }

    @Test
    void testProductEqualsAndHashCode() {
        Product product1 = Product.builder().id("1").name("Produto Teste").build();
        Product product2 = Product.builder().id("1").name("Produto Teste").build();

        assertEquals(product1, product2);
        assertEquals(product1.hashCode(), product2.hashCode());

        product2.setName("Outro Produto");
        assertNotEquals(product1, product2);
    }

    @Test
    void testProductToString() {
        Product product = Product.builder().id("1").name("Produto Teste").build();
        String expectedToString = "Product(id=1, name=Produto Teste)";
        assertEquals(expectedToString, product.toString());
    }
}
