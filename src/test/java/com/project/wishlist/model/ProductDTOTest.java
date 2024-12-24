package com.project.wishlist.model;

import com.project.wishlist.dto.ProductDTO;
import com.project.wishlist.dto.WishListMapper;
import com.project.wishlist.model.Product;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class ProductDTOTest {

    private final WishListMapper productMapper = Mappers.getMapper(WishListMapper.class);

    @Test
    void shouldReturnNullWhenProductDTOIsNull() {
        assertNull(productMapper.productDTOToProduct(null));
    }

    @Test
    void shouldReturnNullWhenProductIsNull() {
        assertNull(productMapper.productToProductDTO(null));
    }

    @Test
    void shouldMapEmptyProductToProductDTO() {
        Product product = Product.builder().id("1").name("product1").build();
        ProductDTO productDTO = productMapper.productToProductDTO(product);

        assertNotNull(productDTO);
        assertEquals("1", productDTO.getId());
        assertEquals("product1", productDTO.getName());
    }

    @Test
    void shouldMapEmptyProductDTOToProduct() {
        ProductDTO productDTO = ProductDTO.builder().id("1").name("product1").build();
        Product product = productMapper.productDTOToProduct(productDTO);

        assertNotNull(product);
        assertEquals("1", product.getId());
        assertEquals("product1", product.getName());
    }
}