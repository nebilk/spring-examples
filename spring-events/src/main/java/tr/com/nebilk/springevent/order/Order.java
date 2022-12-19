package tr.com.nebilk.springevent.order;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import tr.com.nebilk.springevent.customer.Customer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@ToString
@Entity(name = "ORDER_TBL")
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue
    private Long id;
    private String message;
    private OrderStatus orderStatus;
    @ManyToOne
    private Customer customer;

}
