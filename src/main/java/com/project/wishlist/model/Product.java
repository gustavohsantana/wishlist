package com.project.wishlist.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
@Builder
public class Product {
    @Indexed(unique = true)
    private String id;
    private String name;
}