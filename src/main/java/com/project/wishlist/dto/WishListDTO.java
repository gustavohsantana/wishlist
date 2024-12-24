package com.project.wishlist.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WishListDTO {
    private String id;
    private String clientId;
    private List<ProductDTO> products;
}
