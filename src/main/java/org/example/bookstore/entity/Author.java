package org.example.bookstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Author {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String bio;
    private String profileImage;
    private LocalDate dateOfBith;
    private String nationality;

    @OneToMany(mappedBy = "author")
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book){
        book.setAuthor(this);
        this.books.add(book);
    }

    public Author(String name, String bio, String profileImage, LocalDate dateOfBith, String nationality) {
        this.name = name;
        this.bio = bio;
        this.profileImage = profileImage;
        this.dateOfBith = dateOfBith;
        this.nationality = nationality;
    }
}
