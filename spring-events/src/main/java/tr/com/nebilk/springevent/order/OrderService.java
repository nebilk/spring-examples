package tr.com.nebilk.springevent.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import tr.com.nebilk.springevent.order.event.OrderCanceledEvent;
import tr.com.nebilk.springevent.order.event.OrderCreatedEvent;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public Order createOrder(Order order){
        log.info("Order creating : {} ", order);
        order.setOrderStatus(OrderStatus.PROCESSING);
        final Order save = orderRepository.save(order);
        log.info("Order saved to DB : {} ", save);
        log.error("Publishing order created event");
        applicationEventPublisher.publishEvent(new OrderCreatedEvent(order));
        if(order.getMessage().equals("exception ticket case")){
            throw new RuntimeException("Ticket mechanism test!");
        }
        return save;
    }

    public Order getOrder(Long orderId) {
        log.info("Get Order byId {} ", orderId);
        final Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found!"));
        log.info("Order found : {} ", order);
        return order;
    }

    public Order cancelOrder(Long orderId){
        final Order order = getOrder(orderId);
        log.info("Order is canceling : {}", order);
        order.setOrderStatus(OrderStatus.CANCELED);
        final Order canceled = orderRepository.save(order);
        log.info("Order status changed to CANCELED from DB : {}", canceled);
        log.error("Publishing order canceled event");
        applicationEventPublisher.publishEvent(new OrderCanceledEvent(canceled));
        return canceled;
    }
}
