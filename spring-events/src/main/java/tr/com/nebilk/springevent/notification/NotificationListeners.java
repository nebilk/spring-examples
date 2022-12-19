package tr.com.nebilk.springevent.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import tr.com.nebilk.springevent.customer.event.CustomerCreatedEvent;
import tr.com.nebilk.springevent.customer.event.CustomerDeletedEvent;
import tr.com.nebilk.springevent.order.event.OrderCanceledEvent;
import tr.com.nebilk.springevent.order.event.OrderCreatedEvent;

@Component
@RequiredArgsConstructor
public class NotificationListeners {

    private final NotificationService notificationService;

    @TransactionalEventListener
    public void onOrderCreatedEvent(OrderCreatedEvent event){
        notificationService.sendOrderCreatingNotification(event.getOrder());
    }

    @TransactionalEventListener
    public void onOrderCanceledEvent(OrderCanceledEvent event){
        notificationService.sendOrderCanceledNotification(event.getOrder());
    }

    @EventListener
    public void onCustomerCreatedEvent(CustomerCreatedEvent event) {
        notificationService.sendCustomerCreatedNotification(event.getCustomer());
    }

    @EventListener
    public void onCustomerDeletedEvent(CustomerDeletedEvent event){
        notificationService.sendCustomerDeletedNotification(event.getCustomer());
    }

}
