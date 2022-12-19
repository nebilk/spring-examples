package tr.com.nebilk.springevent.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.createCustomer(customer), HttpStatus.CREATED);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> getCustomer(@PathVariable("customerId") Long customerId) {
        return ResponseEntity.ok(customerService.getCustomer(customerId));
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("customerId") Long customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.ok().body(null);
    }
}