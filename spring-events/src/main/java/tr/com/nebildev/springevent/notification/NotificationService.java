package tr.com.nebildev.springevent.notification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tr.com.nebildev.springevent.customer.Customer;
import tr.com.nebildev.springevent.order.Order;

@Service
@Slf4j
public class NotificationService {

    public void sendOrderCreatingNotification(Order order) {
        log.info("Order creating : {} ", order);
    }

    public void sendOrderCanceledNotification(Order order) {
        log.info("Order canceled : {} ", order);
    }


    public void sendCustomerCreatedNotification(Customer customer) {
        log.info("Customer created : {} ", customer);
    }

    public void sendCustomerDeletedNotification(Customer customer) {
        log.info("Customer deleted: {} ", customer);
    }
}
