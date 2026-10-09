package org.example.bookstore.dto;

import java.time.LocalDate;
import java.util.UUID;

/*
private UUID id;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String bio;
    private String profileImage;
    private LocalDate dateOfBith;
    private String nationality;
 */
public record AuthorDto(
        UUID id,
        String name,
        String bio,
        String profileImage,
        LocalDate dateOfBith,
        String nationality
) {
}
