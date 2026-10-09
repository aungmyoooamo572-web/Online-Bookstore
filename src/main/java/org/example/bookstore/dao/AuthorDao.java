package org.example.bookstore.dao;

import org.example.bookstore.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorDao extends JpaRepository<Author, UUID> {
}
