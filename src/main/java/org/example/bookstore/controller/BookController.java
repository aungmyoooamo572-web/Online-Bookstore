package org.example.bookstore.controller;

import lombok.RequiredArgsConstructor;
import org.example.bookstore.dto.AuthorDto;
import org.example.bookstore.dto.BookDto;
import org.example.bookstore.dto.CategoryDto;
import org.example.bookstore.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;
    @GetMapping("/author")
    public List<AuthorDto> listAllAuthor(){
        return bookService.listAllAuthor();
    }
    @GetMapping("/category")
    public List<CategoryDto> listAllCategory(){
        return bookService.listAllCategory();
    }
    // http://localhost:8080/api/books/{id}
    @GetMapping("/{id}")
    public BookDto findBookById(@PathVariable UUID id) {
        return bookService.findBookById(id);
    }


    // http://localhost:8080/api/books/list?page=0&size=20&sort=price,desc
    @GetMapping("/list")
    public ResponseEntity<Page<BookDto>> findAllBooks(
            @PageableDefault(size = 20,sort = "title") Pageable pageable) {
        Page<BookDto> books=bookService.findAllBooks(pageable);
        return ResponseEntity.ok(books);
    }
    // http://localhost:8080/api/books/author/{authorName}
    @GetMapping("/author/{authorName}")
    public List<BookDto> listBooksByAuthor(@PathVariable String authorName){
        return bookService.listBooksByAuthor(authorName);
    }
    // http://localhost:8080/api/books/category/{categoryName}
    @GetMapping("/category/{categoryName}")
    public List<BookDto> listBooksByCategory(@PathVariable String categoryName){
        return bookService.listBooksByCategory(categoryName);
    }


    // http://localhost:8080/api/books/all?page=0&size=40
    @GetMapping("/all")
    public ResponseEntity<Page<BookDto>> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ){
        Page<BookDto> books = bookService.findBooksWithPagination(page, size);
        return ResponseEntity.ok(books);
    }
}
