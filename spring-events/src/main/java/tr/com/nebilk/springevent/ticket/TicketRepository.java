package tr.com.nebilk.springevent.ticket;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tr.com.nebilk.springevent.order.Order;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class TicketRepository {

    @SneakyThrows
    public String createTicket(Order order) {
        TimeUnit.SECONDS.sleep(1);
        final String ticketNumber = UUID.randomUUID().toString();
        log.info("Ticket created for order : {}, ticket number is : {} ", order, ticketNumber);
        return ticketNumber;
    }
}
