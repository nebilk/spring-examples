package tr.com.nebilk.springevent.analytics;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import tr.com.nebilk.springevent.customer.event.CustomerCreatedEvent;
import tr.com.nebilk.springevent.customer.event.CustomerDeletedEvent;
import tr.com.nebilk.springevent.order.event.OrderCanceledEvent;
import tr.com.nebilk.springevent.order.event.OrderCreatedEvent;

@Component
@RequiredArgsConstructor
public class AnalyticsListeners {
    private final AnalyticsService analyticsService;

    @Async
    @EventListener
    public void onCustomerCreateEvent(CustomerCreatedEvent event){
        analyticsService.sendNewCustomerToGoogleAnalytics(event.getCustomer());
    }

    @Async
    @EventListener
    public void onCustomerDeleteEvent(CustomerDeletedEvent event){
        analyticsService.sendDeletedCustomerToGoogleAnalytics(event.getCustomer());
    }

    @Async
    @EventListener
    public void onOrderCreatedEvent(OrderCreatedEvent event){
        analyticsService.sendNewOrderToGoogleAnalytics(event.getOrder());
    }

    @Async
    @EventListener
    public void onOrderCanceledEvent(OrderCanceledEvent event){
        analyticsService.sendCancelOrderToGoogleAnalytics(event.getOrder());
    }
}
