package com.project.wishlist.controller;

import com.project.wishlist.config.SecurityConfig;
import com.project.wishlist.dto.ProductDTO;
import com.project.wishlist.dto.WishListDTO;
import com.project.wishlist.service.WishListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WishListController.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = {SecurityConfig.class, WishListController.class})
class WishListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WishListService wishListService;

    private ProductDTO productDTO;
    private WishListDTO wishListDTO;

    @BeforeEach
    void setUp() {
        productDTO = ProductDTO.builder().id("1").name("Produto Teste").build();
        wishListDTO = WishListDTO.builder().clientId("clientID01XPTO").products(List.of(productDTO)).build();
    }

    @Test
    void testAddProductToWishList() throws Exception {
        when(wishListService.addProductToWishList(anyString(), any(ProductDTO.class))).thenReturn(wishListDTO);

        mockMvc.perform(post("/api/wishlist/{clientId}/add", "clientID01XPTO")
                        .with(httpBasic("user", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"1\",\"name\":\"Produto Teste\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientId").value("clientID01XPTO"))
                .andExpect(jsonPath("$.products[0].id").value("1"))
                .andExpect(jsonPath("$.products[0].name").value("Produto Teste"));

        verify(wishListService, times(1)).addProductToWishList(anyString(), any(ProductDTO.class));
    }

    @Test
    void testRemoveProductFromWishList() throws Exception {
        wishListDTO.setProducts(new ArrayList<>(List.of(productDTO)));
        wishListDTO.getProducts().clear();
        when(wishListService.removeProductFromWishList(anyString(), anyString())).thenReturn(wishListDTO);

        mockMvc.perform(delete("/api/wishlist/{clientId}/products", "clientID01XPTO")
                        .with(httpBasic("user", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"1\",\"name\":\"Produto Teste\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientId").value("clientID01XPTO"))
                .andExpect(jsonPath("$.products").isEmpty());

        verify(wishListService, times(1)).removeProductFromWishList(anyString(), anyString());
    }

    @Test
    void testGetAllProductsFromWishList() throws Exception {
        when(wishListService.getAllProductsFromWishList("clientID01XPTO")).thenReturn(List.of(productDTO));

        mockMvc.perform(get("/api/wishlist/{clientId}", "clientID01XPTO")
                        .with(httpBasic("user", "password")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("Produto Teste"));

        verify(wishListService, times(1)).getAllProductsFromWishList("clientID01XPTO");
    }

    @Test
    void testIsProductInWishList() throws Exception {
        when(wishListService.isProductInWishList("clientID01XPTO", "1")).thenReturn(true);

        mockMvc.perform(get("/api/wishlist/{clientId}/product/{productId}", "clientID01XPTO", "1")
                        .with(httpBasic("user", "password")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));

        verify(wishListService, times(1)).isProductInWishList("clientID01XPTO", "1");
    }

    @Test
    void addProductToWishListReturnsBadRequestWhenProductNameNull() throws Exception {
        ProductDTO invalidProductDTO = ProductDTO.builder().id("1").build();

        mockMvc.perform(post("/api/wishlist/{clientId}/add", "clientID01XPTO")
                        .with(httpBasic("user", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"1\"}"))
                .andExpect(status().isBadRequest());

        verify(wishListService, never()).addProductToWishList(anyString(), any(ProductDTO.class));
    }

    @Test
    void removeProductFromWishListReturnsBadRequestWhenProductDTONull() throws Exception {
        mockMvc.perform(delete("/api/wishlist/{clientId}/products", "clientID01XPTO")
                        .with(httpBasic("user", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());

        verify(wishListService, never()).removeProductFromWishList(anyString(), anyString());
    }

    @Test
    void removeProductFromWishListReturnsBadRequestWhenProductIdNull() throws Exception {
        ProductDTO invalidProductDTO = ProductDTO.builder().name("Produto Teste").build();

        mockMvc.perform(delete("/api/wishlist/{clientId}/products", "clientID01XPTO")
                        .with(httpBasic("user", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Produto Teste\"}"))
                .andExpect(status().isBadRequest());

        verify(wishListService, never()).removeProductFromWishList(anyString(), anyString());
    }
}