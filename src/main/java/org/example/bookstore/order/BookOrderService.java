package org.example.bookstore.order;

import lombok.RequiredArgsConstructor;
import org.example.bookstore.dto.BookDto;
import org.example.bookstore.entity.Book;
import org.example.bookstore.security.UserDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookOrderService {
    private final BookOrderDao bookOrderDao;
    private final UserDao userDao;
    @Transactional
    public String saveBookOrder(String username, List<BookOrderController.PurchaseItem> orderItems){
        var user = userDao.findByUsernameAndEmail(username)
                .orElse(null);
        if(user==null){
            return "User not found";
        }
        BookOrder bookOrder=new BookOrder();
        bookOrder.addUser(user);
        for(BookOrderController.PurchaseItem item:orderItems){
            bookOrder.addOrderItem(toOrderItem(item));
        }
        bookOrder.setTotalAmount(orderItems.stream()
                .mapToDouble(item -> item.price() * item.quantity())
                .sum());
        bookOrderDao.save(bookOrder);
        return "Order saved successfully";
    }

    public OrderItem toOrderItem(BookOrderController.PurchaseItem item){
        return new OrderItem(
                null,
                item.title(),
                item.isbn(),
                item.summary(),
                item.price(),
                item.coverImage(),
                item.categoryName(),
                item.authorName(),
                item.quantity()
        );
    }

    public List<BookOrderResponse> getBooksByUser(String name) {
        return getBooksByLoggedInUser(name);
    }

    private List<BookOrderResponse> getBooksByLoggedInUser(String name) {
        List<BookOrderResponse> books = new ArrayList<>();
        List<BookOrder> bookOrders=bookOrderDao.getBooksByLoggedInUser(name);
        for(BookOrder bookOrder:bookOrders){
            List<OrderItem> orderItems=bookOrder.getOrderItems();
            for(OrderItem orderItem:orderItems){
                books.add(toBookDto(orderItem));
            }
        }
        return books;
    }

    private BookOrderResponse toBookDto(OrderItem orderItem){
        return new BookOrderResponse(
                orderItem.getId(),
                orderItem.getTitle(),
                orderItem.getIsbn(),
                orderItem.getSummary(),
                orderItem.getPrice(),
                orderItem.getCoverImage(),
                orderItem.getQuantity(),
                orderItem.getCategoryName(),
                orderItem.getAuthorName()
                );
    }


}
