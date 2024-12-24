package com.project.wishlist.service;

import com.project.wishlist.dto.ProductDTO;
import com.project.wishlist.dto.WishListDTO;
import com.project.wishlist.dto.WishListMapper;
import com.project.wishlist.model.Product;
import com.project.wishlist.model.WishList;
import com.project.wishlist.repository.WishListRepository;
import com.project.wishlist.service.impl.WishListServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class WishListServiceTest {

    @Mock
    private WishListRepository wishListRepository;

    @Mock
    private WishListMapper wishListMapper;

    @InjectMocks
    private WishListServiceImpl wishListService;

    private ProductDTO productDTO;
    private WishListDTO wishListDTO;
    private WishList wishList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productDTO = ProductDTO.builder().id("1").name("Produto Teste").build();
        wishListDTO = WishListDTO.builder().clientId("clientID01").products(List.of(productDTO)).build();
        Product product = Product.builder().id("1").name("Produto Teste").build();
        wishList = WishList.builder().clientId("clientID01").products(new ArrayList<>(List.of(product))).build();
    }

    @Test
    void testAddProductToWishList() {
        WishList emptyWishList = WishList.builder().products(new ArrayList<>()).build();
        when(wishListRepository.findByClientId(anyString())).thenReturn(Optional.of(emptyWishList));
        when(wishListMapper.productDTOToProduct(any(ProductDTO.class))).thenReturn(Product.builder().build());
        when(wishListRepository.save(any(WishList.class))).thenReturn(wishList);
        when(wishListMapper.wishListToWishListDTO(any(WishList.class))).thenReturn(wishListDTO);

        WishListDTO result = wishListService.addProductToWishList("clientID01", productDTO);

        assertNotNull(result);
        assertEquals("clientID01", result.getClientId());
        verify(wishListRepository, times(1)).save(any(WishList.class));
    }

    @Test
    void testRemoveProductFromWishList() {
        when(wishListRepository.findByClientId(anyString())).thenReturn(Optional.of(wishList));
        when(wishListRepository.save(any(WishList.class))).thenReturn(wishList);
        when(wishListMapper.wishListToWishListDTO(any(WishList.class))).thenReturn(wishListDTO);

        WishListDTO result = wishListService.removeProductFromWishList("clientID01", "1");

        assertNotNull(result);
        assertEquals("clientID01", result.getClientId());
        verify(wishListRepository, times(1)).save(any(WishList.class));
    }

    @Test
    void testGetAllProductsFromWishList() {
        when(wishListRepository.findByClientId(anyString())).thenReturn(Optional.of(wishList));
        when(wishListMapper.productToProductDTO(any(Product.class))).thenReturn(productDTO);

        List<ProductDTO> result = wishListService.getAllProductsFromWishList("clientID01");

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("1", result.get(0).getId());
        verify(wishListRepository, times(1)).findByClientId(anyString());
    }

    @Test
    void testIsProductInWishList() {
        when(wishListRepository.existsByClientIdAndProductsId(anyString(), anyString())).thenReturn(true);

        boolean result = wishListService.isProductInWishList("clientID01", "1");

        assertTrue(result);
        verify(wishListRepository, times(1)).existsByClientIdAndProductsId(anyString(), anyString());
    }

    @Test
    void addProductToWishListThrowsExceptionWhenLimitReached() {
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            products.add(Product.builder().id(String.valueOf(i)).build());
        }
        wishList.setProducts(products);
        when(wishListRepository.findByClientId(anyString())).thenReturn(Optional.of(wishList));

        ProductDTO newProductDTO = ProductDTO.builder().id("21").name("Produto Teste 21").build();

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            wishListService.addProductToWishList("clientID05", newProductDTO);
        });

        assertEquals("A wishlist atingiu o limite máximo de 20 produtos.", exception.getMessage());
        verify(wishListRepository, never()).save(any(WishList.class));
    }

    @Test
    void addProductToWishListThrowsExceptionWhenProductExists() {
        when(wishListRepository.findByClientId(anyString())).thenReturn(Optional.of(wishList));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            wishListService.addProductToWishList("clientID01", productDTO);
        });

        assertEquals("O produto já está na wishlist.", exception.getMessage());
        verify(wishListRepository, never()).save(any(WishList.class));
    }
}