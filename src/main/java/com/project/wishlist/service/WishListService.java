package com.project.wishlist.service;

import com.project.wishlist.dto.ProductDTO;
import com.project.wishlist.dto.WishListDTO;

import java.util.List;

public interface WishListService {
    WishListDTO addProductToWishList(String clientId, ProductDTO productDTO);
    WishListDTO removeProductFromWishList(String clientId, String productId);
    List<ProductDTO> getAllProductsFromWishList(String clientId);
    boolean isProductInWishList(String clientId, String productId);
}