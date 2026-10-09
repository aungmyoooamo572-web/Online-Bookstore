package org.example.bookstore.order;

import lombok.RequiredArgsConstructor;
import org.example.bookstore.dto.BookDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class BookOrderController {
    private final BookOrderService bookOrderService;

    public record PurchaseItem(
            UUID id,
            String authorName,
            String categoryName,
            String coverImage,
            Integer isbn,
            String summary,
            Double price,
            Integer quantity,
            String title
    ){}
    @GetMapping
    public List<BookOrderResponse> getBooksByLoggedInUser(Principal principal){
        return bookOrderService.getBooksByUser(principal.getName());
    }


    @PostMapping
    public ResponseEntity<String> saveOrder(@RequestBody List<PurchaseItem> orderRequest,
                                            Principal principal){
        bookOrderService.saveBookOrder(principal.getName(),orderRequest);
        String message="%s's Order saved successfully"
                .formatted(principal.getName());
        return ResponseEntity.ok(message);
    }

}
