package tr.com.nebilk.springevent.customer.event;

import lombok.Data;
import tr.com.nebilk.springevent.customer.Customer;

@Data
public class CustomerCreatedEvent {
    private final Customer customer;
}
