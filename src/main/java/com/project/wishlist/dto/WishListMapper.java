package com.project.wishlist.dto;

import com.project.wishlist.model.Product;
import com.project.wishlist.model.WishList;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface WishListMapper {

    WishListMapper INSTANCE = Mappers.getMapper(WishListMapper.class);

    WishListDTO wishListToWishListDTO(WishList wishList);

    ProductDTO productToProductDTO(Product product);

    WishList wishListDTOToWishList(WishListDTO wishListDTO);

    Product productDTOToProduct(ProductDTO productDTO);
}

