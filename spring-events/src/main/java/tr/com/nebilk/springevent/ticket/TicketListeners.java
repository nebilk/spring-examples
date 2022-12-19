package tr.com.nebilk.springevent.ticket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import tr.com.nebilk.springevent.order.event.OrderCreatedEvent;

@Component
@RequiredArgsConstructor
public class TicketListeners {

    private final TicketService ticketService;

    // An error occur so we need to create a ticket about order
    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void onOrderCreationFailedEvent(OrderCreatedEvent event) {
        ticketService.createTicket(event.getOrder());
    }

}
