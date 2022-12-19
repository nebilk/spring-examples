package tr.com.nebilk.springevent.order.event;

import lombok.Data;
import tr.com.nebilk.springevent.order.Order;

@Data
public class OrderCreatedEvent {
    private final Order order;
}
