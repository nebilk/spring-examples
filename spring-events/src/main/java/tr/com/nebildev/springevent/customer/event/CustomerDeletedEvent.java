package tr.com.nebildev.springevent.customer.event;

import lombok.Data;
import tr.com.nebildev.springevent.customer.Customer;

@Data
public class CustomerDeletedEvent {
    private final Customer customer;
}
