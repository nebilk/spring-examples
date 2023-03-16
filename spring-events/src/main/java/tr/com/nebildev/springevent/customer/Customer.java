package tr.com.nebildev.springevent.customer;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@ToString
@Entity
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String email;

}
