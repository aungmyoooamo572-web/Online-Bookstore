package org.example.bookstore.order;

import org.example.bookstore.dto.BookDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface BookOrderDao extends JpaRepository<BookOrder, UUID> {
    @Query("""
    select b from BookOrder b
    left join b.user u
    where u.username = :name
    """)
    List<BookOrder> getBooksByLoggedInUser(String name);

}
