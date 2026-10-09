package org.example.bookstore.order;

import java.util.UUID;

public record BookOrderResponse(
        UUID id,
        String title,
        Integer isbn,
        String summary,
        double price,
        String coverImage,
        int stockQuantity,
        String categoryName,
        String authorName
) {
}
