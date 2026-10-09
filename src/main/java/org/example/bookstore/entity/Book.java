package org.example.bookstore.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Book {
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

    public Book(UUID id, String title, Integer isbn, String summary, double price, String coverImage, int stockQuantity, Status status) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.summary = summary;
        this.price = price;
        this.coverImage = coverImage;
        this.stockQuantity = stockQuantity;
        this.status = status;
    }

    public enum Status {
        ORDERED,
        IN_STOCK,
        OUT_OF_STOCK
    }
}
