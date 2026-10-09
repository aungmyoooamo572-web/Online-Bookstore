package org.example.bookstore.dto;

import java.util.UUID;

/*
private UUID id;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String image;
    private String slug;
 */
public record CategoryDto(
        UUID id,
        String name,
        String description,
        String image,
        String slug
) {
}
