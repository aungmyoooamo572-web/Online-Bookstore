package org.example.bookstore.dao;

import org.example.bookstore.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryDao extends JpaRepository<Category, UUID> {
}
