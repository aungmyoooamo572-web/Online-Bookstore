package org.example.bookstore.dao;

import org.example.bookstore.dto.BookDto;
import org.example.bookstore.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookDao extends JpaRepository<Book, UUID> {
    @Query("""
 select new org.example.bookstore.dto.BookDto(
                b.id,
                b.title,
                b.isbn,
                b.summary,
                b.price,
                b.coverImage,
                b.stockQuantity,
                b.status,
                c.name,
                a.name
            ) 
            from Book b 
            left join b.category c 
            left join b.author a where b.id =:id
""")
    Optional<BookDto> findBookById(@Param("id") UUID id);

    @Query("""
 select new org.example.bookstore.dto.BookDto(
                b.id,
                b.title,
                b.isbn,
                b.summary,
                b.price,
                b.coverImage,
                b.stockQuantity,
                b.status,
                c.name,
                a.name
            ) 
            from Book b 
            left join b.category c 
            left join b.author a where a.name =:name
""")
    List<BookDto> findBookByAuthorName(@Param("name") String name);

    @Query("""
select new org.example.bookstore.dto.BookDto(
                b.id,
                b.title,
                b.isbn,
                b.summary,
                b.price,
                b.coverImage,
                b.stockQuantity,
                b.status,
                c.name,
                a.name
            ) 
            from Book b 
            left join b.category c 
            left join b.author a where c.name =:name
""")
    List<BookDto> findBookByCategoryName(@Param("name") String name);

    @Query(
            value = """
            select new org.example.bookstore.dto.BookDto(
                b.id,
                b.title,
                b.isbn,
                b.summary,
                b.price,
                b.coverImage,
                b.stockQuantity,
                b.status,
                c.name,
                a.name
            ) 
            from Book b 
            left join b.category c 
            left join b.author a
            """,
            countQuery = "select count(b) from Book b"
    )
    Page<BookDto> listAllBooks(Pageable pageable);
}