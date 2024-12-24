package com.project.wishlist.controller;

import com.project.wishlist.dto.ProductDTO;
import com.project.wishlist.dto.WishListDTO;
import com.project.wishlist.service.WishListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
@Tag(name = "wishlist-service", description = "APIs for WishList management")
public class WishListController {

    private static final Logger logger = LoggerFactory.getLogger(WishListController.class);
    private final WishListService wishListService;

    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    @Operation(summary = "Add a product to the wishlist")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product added to wishlist"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/{clientId}/add")
    public ResponseEntity<WishListDTO> addProductToWishList(
            @Parameter(description = "Client ID") @PathVariable String clientId,
            @Parameter(description = "Product to add") @RequestBody ProductDTO productDTO) {
        logger.info("Adding product to wishlist for client: {}", clientId);
        if (productDTO == null || productDTO.getId() == null || productDTO.getName() == null) {
            logger.warn("Invalid product data: {}", productDTO);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        WishListDTO updatedWishList = wishListService.addProductToWishList(clientId, productDTO);
        logger.info("Product added successfully: {}", productDTO);
        return new ResponseEntity<>(updatedWishList, HttpStatus.OK);
    }

    @Operation(summary = "Remove a product from the wishlist")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product removed from wishlist"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{clientId}/products")
    public ResponseEntity<WishListDTO> removeProductFromWishList(
            @Parameter(description = "Client ID") @PathVariable String clientId,
            @Parameter(description = "Product to remove") @RequestBody ProductDTO productDTO) {
        logger.info("Removing product from wishlist for client: {}", clientId);
        if (productDTO == null || productDTO.getId() == null) {
            logger.warn("Invalid product data: {}", productDTO);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        WishListDTO updatedWishList = wishListService.removeProductFromWishList(clientId, productDTO.getId());
        logger.info("Product removed successfully: {}", productDTO);
        return new ResponseEntity<>(updatedWishList, HttpStatus.OK);
    }

    @Operation(summary = "Get all products from the wishlist")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of products"),
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{clientId}")
    public ResponseEntity<List<ProductDTO>> getAllProductsFromWishList(
            @Parameter(description = "Client ID") @PathVariable String clientId) {
        logger.info("Fetching all products from wishlist for client: {}", clientId);
        List<ProductDTO> products = wishListService.getAllProductsFromWishList(clientId);
        logger.info("Fetched products: {}", products);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @Operation(summary = "Check if a product is in the wishlist")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product presence status"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{clientId}/product/{productId}")
    public ResponseEntity<Boolean> isProductInWishList(
            @Parameter(description = "Client ID") @PathVariable String clientId,
            @Parameter(description = "Product ID") @PathVariable String productId) {
        logger.info("Checking if product is in wishlist for client: {}", clientId);
        boolean isProductInWishList = wishListService.isProductInWishList(clientId, productId);
        logger.info("Product status: {}", isProductInWishList);
        return new ResponseEntity<>(isProductInWishList, HttpStatus.OK);
    }
}