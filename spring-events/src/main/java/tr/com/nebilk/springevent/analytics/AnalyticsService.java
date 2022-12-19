package tr.com.nebilk.springevent.analytics;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tr.com.nebilk.springevent.customer.Customer;
import tr.com.nebilk.springevent.order.Order;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class AnalyticsService {

    @SneakyThrows
    public void sendNewCustomerToGoogleAnalytics(Customer customer) {
        log.info("Google Analytics service is calling for new customer : {} ", customer);
        TimeUnit.SECONDS.sleep(5);
        log.info("Google Analytics service called for new customer : {} ", customer);
    }

    @SneakyThrows
    public void sendDeletedCustomerToGoogleAnalytics(Customer customer) {
        log.info("Google Analytics deleted user service is calling for {} : ", customer);
        TimeUnit.SECONDS.sleep(10);
        log.info("Google Analytics deleted user service called for {} : ", customer);
    }

    @SneakyThrows
    public void sendNewOrderToGoogleAnalytics(Order order) {
        log.info("Google Analytics new order is calling for {} : ", order);
        TimeUnit.SECONDS.sleep(10);
        log.info("Google Analytics new order called for {} : ", order);
    }

    @SneakyThrows
    public void sendCancelOrderToGoogleAnalytics(Order order) {
        log.info("Google Analytics cancel order is calling for {} : ", order);
        TimeUnit.SECONDS.sleep(10);
        log.info("Google Analytics cancel order called for {} : ", order);
    }

}
