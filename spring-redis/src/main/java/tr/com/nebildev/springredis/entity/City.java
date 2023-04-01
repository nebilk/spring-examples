package tr.com.nebildev.springredis.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@RedisHash("cities_tbl")
@Data
@EqualsAndHashCode
public class City implements Serializable {

    @Id
    private String id;
    private String name;
    @Indexed
    private String code;
    private String shortName;
}