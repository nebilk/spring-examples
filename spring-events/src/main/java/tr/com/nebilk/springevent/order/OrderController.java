package tr.com.nebilk.springevent.order;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.com.nebilk.springevent.customer.Customer;
import tr.com.nebilk.springevent.customer.CustomerService;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        return new ResponseEntity<>(orderService.createOrder(order), HttpStatus.CREATED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable("orderId") Long orderId) {
        return ResponseEntity.ok(orderService.getOrder(orderId));
    }

    @PostMapping("/{orderId}")
    public ResponseEntity<Order> cancelOrder(@PathVariable("orderId") Long orderId) {
        return ResponseEntity.ok(orderService.cancelOrder(orderId));
    }

}
