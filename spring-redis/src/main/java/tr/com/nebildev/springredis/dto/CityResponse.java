package tr.com.nebildev.springredis.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class CityResponse {
    private String id;
    private String name;
    private String code;
    private String shortName;
}
