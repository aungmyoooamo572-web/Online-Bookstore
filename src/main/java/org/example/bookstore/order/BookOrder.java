package org.example.bookstore.order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.bookstore.security.User;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class BookOrder {
    @Id
    @GeneratedValue
    private UUID id;
    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "bookOrder",
    cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    private List<OrderItem> orderItems=
            new ArrayList<>();

    public void addUser(User user){
        this.user=user;
    }

    public void addOrderItem(OrderItem orderItem){
        orderItem.setBookOrder(this);
        orderItems.add(orderItem);

    }

    private Double totalAmount;
    private LocalDateTime createdAt=LocalDateTime.now();
}
