package org.example.bookstore.order;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/*
export interface CartItem{
    id:string;
    title:string;
    isbn:number;
    summary:string;
    price:number;
    coverImage:string;
    categoryName:string;
    authorName:string;
    quantity:number;
}
 */
@Entity
@Getter
@Setter
@NoArgsConstructor

public class OrderItem {
    @Id
    @GeneratedValue
    private UUID id;
    private String title;
    private Integer isbn;
    private String summary;
    private Double price;
    private String coverImage;
    private String categoryName;
    private String authorName;
    private Integer quantity;

    public OrderItem(UUID id, String title, Integer isbn, String summary, Double price, String coverImage, String categoryName, String authorName, Integer quantity) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.summary = summary;
        this.price = price;
        this.coverImage = coverImage;
        this.categoryName = categoryName;
        this.authorName = authorName;
        this.quantity = quantity;
    }

    @ManyToOne
    private BookOrder bookOrder;
}
