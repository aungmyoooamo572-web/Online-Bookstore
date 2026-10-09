package org.example.bookstore.dto;

import org.example.bookstore.entity.Book;

import java.util.UUID;

/*
    @Id
    @GeneratedValue
    private UUID id;
    private String title;
    private Integer isbn; //10 digits
    @Column(columnDefinition = "TEXT")
    private String summary;
    private double price;
    private String coverImage;
    private int stockQuantity;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;
 */
public record BookDto(
    UUID id,
    String title,
    Integer isbn,
    String summary,
    double price,
    String coverImage,
    int stockQuantity,
    Book.Status status,
    String categoryName,
    String authorName
) {
}
