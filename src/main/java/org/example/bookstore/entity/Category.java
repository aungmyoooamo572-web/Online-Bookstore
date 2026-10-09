package org.example.bookstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String image;
    private String slug; //for short code sci-fi . tech

    @OneToMany(mappedBy = "category")
    private List<Book> books=
            new ArrayList<>();

    public void addBook(Book book){
        book.setCategory(this);
        this.books.add(book);
    }

    public Category(String name,String description,
                    String image,String slug){
        this.name =name;
        this.description=description;
        this.image=image;
        this.slug=slug;
    }


}
