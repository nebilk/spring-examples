package tr.com.nebilk.springevent.ticket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tr.com.nebilk.springevent.order.Order;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketService {

    private final TicketRepository ticketRepository;

    public String createTicket(Order order){
        log.info("Ticket creating process is starting for order : {}", order);
        final String ticketNumber = ticketRepository.createTicket(order);
        log.info("Ticket creating process is finished for order : {}", order);
        return ticketNumber;
    }


}
