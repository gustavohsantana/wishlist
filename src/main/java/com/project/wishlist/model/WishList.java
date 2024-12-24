package com.project.wishlist.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Iterator;
import java.util.List;

@Document(collection = "wishlists")
@Data
@Builder
@AllArgsConstructor
public class WishList implements Iterable<Product>{
    @Id
    private String id;
    @Indexed(unique = true)
    private String clientId;
    private List<Product> products;

    @Override
    public Iterator<Product> iterator() {
        return products.iterator();
    }
}
