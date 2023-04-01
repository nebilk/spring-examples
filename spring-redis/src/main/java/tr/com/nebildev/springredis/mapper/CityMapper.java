package tr.com.nebildev.springredis.mapper;

import org.springframework.stereotype.Component;
import tr.com.nebildev.springredis.dto.CityResponse;
import tr.com.nebildev.springredis.entity.City;

import java.util.function.Function;

@Component
public class CityMapper implements Function<City, CityResponse> {
    @Override
    public CityResponse apply(City city) {
        return CityResponse.builder()
                .id(city.getId())
                .code(city.getCode())
                .name(city.getName())
                .shortName(city.getShortName())
                .build();
    }
}
