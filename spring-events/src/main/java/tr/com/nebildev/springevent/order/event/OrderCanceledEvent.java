package tr.com.nebildev.springevent.order.event;

import lombok.Data;
import tr.com.nebildev.springevent.order.Order;

@Data
public class OrderCanceledEvent {
    private final Order order;
}
