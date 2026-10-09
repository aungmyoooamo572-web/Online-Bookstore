package org.example.bookstore;

import lombok.RequiredArgsConstructor;
import org.example.bookstore.order.BookOrderDao;
import org.example.bookstore.order.BookOrderService;
import org.example.bookstore.service.BookService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@RequiredArgsConstructor
public class BookstoreApplication {
    private final BookService bookService;

    private final BookOrderService bookOrderService;

    @Bean
    public ApplicationRunner runner(){
        return r->{
            //bookService.initData();
//            bookOrderService
//                    .getBooksByUser("mary")
//                    .forEach(System.out::println);
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(BookstoreApplication.class, args);
    }

}
