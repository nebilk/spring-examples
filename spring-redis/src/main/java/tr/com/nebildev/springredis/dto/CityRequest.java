package tr.com.nebildev.springredis.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityRequest {
    private String name;
    private String code;
    private String shortName;
}