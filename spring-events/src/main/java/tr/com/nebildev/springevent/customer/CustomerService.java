package tr.com.nebildev.springevent.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import tr.com.nebildev.springevent.customer.event.CustomerCreatedEvent;
import tr.com.nebildev.springevent.customer.event.CustomerDeletedEvent;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {
    private final CustomerRepository customerRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    public Customer createCustomer(Customer customer){
        log.info("Customer creating... {} ", customer);
        final Customer save = customerRepository.save(customer);
        log.info("Customer saved to DB : {} ", save);
        log.error("Publishing customer created event");
        applicationEventPublisher.publishEvent(new CustomerCreatedEvent(customer));
        return save;
    }

    public Customer getCustomer(Long customerId){
        log.info("Get Customer byId {} ", customerId);
        final Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found!"));
        log.info("Customer found : {} ", customer);
        return customer;
    }

    public void deleteCustomer(Long customerId){
        final Customer customer = getCustomer(customerId);
        log.info("Customer deleting {} ", customer);
        customerRepository.delete(customer);
        log.info("Customer deleted from DB {} ", customer);
        log.error("Publishing customer deleted event");
        applicationEventPublisher.publishEvent(new CustomerDeletedEvent(customer));
    }
}
