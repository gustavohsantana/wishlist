package com.project.wishlist.dto;

import com.project.wishlist.model.Product;
import com.project.wishlist.model.WishList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WishListMapperTest {

    private WishListMapper wishListMapper;

    @BeforeEach
    void setUp() {
        wishListMapper = Mappers.getMapper(WishListMapper.class);
    }

    @Test
    void shouldMapProductDTOToProduct() {
        ProductDTO productDTO = ProductDTO.builder()
                .id("1")
                .name("Produto Teste")
                .build();

        Product product = wishListMapper.productDTOToProduct(productDTO);

        assertNotNull(product);
        assertEquals(productDTO.getId(), product.getId());
        assertEquals(productDTO.getName(), product.getName());
    }

    @Test
    void shouldMapProductToProductDTO() {
        Product product = Product.builder()
                .id("1")
                .name("Produto Teste")
                .build();

        ProductDTO productDTO = wishListMapper.productToProductDTO(product);

        assertNotNull(productDTO);
        assertEquals(product.getId(), productDTO.getId());
        assertEquals(product.getName(), productDTO.getName());
    }

    @Test
    void shouldMapWishListToWishListDTO() {
        List<Product> products = new ArrayList<>();
        products.add(Product.builder().id("1").name("Produto Teste").build());

        WishList wishList = WishList.builder()
                .id("1")
                .clientId("cliente1")
                .products(products)
                .build();

        WishListDTO wishListDTO = wishListMapper.wishListToWishListDTO(wishList);

        assertNotNull(wishListDTO);
        assertEquals(wishList.getId(), wishListDTO.getId());
        assertEquals(wishList.getClientId(), wishListDTO.getClientId());
        assertEquals(wishList.getProducts().size(), wishListDTO.getProducts().size());
    }

    @Test
    void shouldMapWishListDTOToWishList() {
        List<ProductDTO> productDTOs = new ArrayList<>();
        productDTOs.add(ProductDTO.builder().id("1").name("Produto Teste").build());

        WishListDTO wishListDTO = WishListDTO.builder()
                .id("1")
                .clientId("cliente1")
                .products(productDTOs)
                .build();

        WishList wishList = wishListMapper.wishListDTOToWishList(wishListDTO);

        assertNotNull(wishList);
        assertEquals(wishListDTO.getId(), wishList.getId());
        assertEquals(wishListDTO.getClientId(), wishList.getClientId());
        assertEquals(wishListDTO.getProducts().size(), wishList.getProducts().size());
    }
}