package com.project.wishlist.service.impl;

import com.project.wishlist.dto.ProductDTO;
import com.project.wishlist.dto.WishListDTO;
import com.project.wishlist.dto.WishListMapper;
import com.project.wishlist.model.WishList;
import com.project.wishlist.repository.WishListRepository;
import com.project.wishlist.service.WishListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishListServiceImpl implements WishListService {

    private static final Logger logger = LoggerFactory.getLogger(WishListServiceImpl.class);
    private final WishListRepository wishListRepository;
    private final WishListMapper wishListMapper;

    public WishListServiceImpl(WishListRepository wishListRepository, WishListMapper wishListMapper) {
        this.wishListRepository = wishListRepository;
        this.wishListMapper = wishListMapper;
    }

    @Override
    @Transactional
    public WishListDTO addProductToWishList(String clientId, ProductDTO productDTO) {
        logger.info("Adding product to wishlist for client: {}", clientId);
        WishList wishList = wishListRepository.findByClientId(clientId)
                .orElseGet(() -> WishList.builder()
                        .clientId(clientId)
                        .products(new ArrayList<>())
                        .build());

        boolean productExists = wishList.getProducts().stream()
                .anyMatch(product -> product.getId().equals(productDTO.getId()));

        if (productExists) {
            throw new RuntimeException("O produto já está na wishlist.");
        }

        if (wishList.getProducts().size() >= 20) {
            throw new RuntimeException("A wishlist atingiu o limite máximo de 20 produtos.");
        }

        wishList.getProducts().add(wishListMapper.productDTOToProduct(productDTO));
        return wishListMapper.wishListToWishListDTO(wishListRepository.save(wishList));
    }

    @Transactional
    public WishListDTO removeProductFromWishList(String clientId, String productId) {
        logger.info("Removing product from wishlist for client: {}", clientId);
        WishList wishList = wishListRepository.findByClientId(clientId)
                .orElseThrow(() -> new RuntimeException("Wishlist não encontrada para o clienteId: " + clientId));

        wishList.getProducts().removeIf(product -> product.getId().equals(productId));
        return wishListMapper.wishListToWishListDTO(wishListRepository.save(wishList));
    }

    @Override
    public List<ProductDTO> getAllProductsFromWishList(String clientId) {
        logger.info("Fetching all products from wishlist for client: {}", clientId);
        return wishListRepository.findByClientId(clientId)
                .orElseThrow(() -> new RuntimeException("Wishlist não encontrada para o clienteId: " + clientId))
                .getProducts().stream()
                .map(wishListMapper::productToProductDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isProductInWishList(String clientId, String productId) {
        logger.info("Checking if product is in wishlist for client: {}", clientId);
        return wishListRepository.existsByClientIdAndProductsId(clientId, productId);
    }
}