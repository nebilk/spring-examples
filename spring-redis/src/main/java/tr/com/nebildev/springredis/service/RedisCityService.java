package tr.com.nebildev.springredis.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tr.com.nebildev.springredis.dto.CityRequest;
import tr.com.nebildev.springredis.dto.CityResponse;
import tr.com.nebildev.springredis.entity.City;
import tr.com.nebildev.springredis.exception.CityFoundException;
import tr.com.nebildev.springredis.exception.CityNotFoundException;
import tr.com.nebildev.springredis.mapper.CityMapper;
import tr.com.nebildev.springredis.repository.RedisCityRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RedisCityService {

    private final RedisCityRepository redisCityRepository;
    private final CityMapper cityMapper;
    private final RedisCacheService redisCacheService;

    public CityResponse getCity(String code) {

        final Optional<Object> cityFromCache = redisCacheService.getCityFromCache(code);
        if (cityFromCache.isPresent()) {
            return cityMapper.apply((City) cityFromCache.get());
        }

        final Optional<City> city = redisCityRepository.findCitiesByCode(code);

        final City cityResponse = city.orElseThrow(() -> new CityNotFoundException("City is not found : " + code));

        redisCacheService.putCityToCache(code, cityResponse);

        return cityMapper.apply(cityResponse);
    }

    public List<CityResponse> getCities() {
        final List<City> cityList = redisCityRepository.findAll();

        return cityList.stream().map(cityMapper).collect(Collectors.toList());
    }

    public CityResponse saveCity(CityRequest cityRequest) {

        final Optional<Object> cityFromCache = redisCacheService.getCityFromCache(cityRequest.getCode());

        if (cityFromCache.isPresent()) {
            return cityMapper.apply((City) cityFromCache.get());
        }

        final Optional<City> cityByCode = redisCityRepository.findCitiesByCode(cityRequest.getCode());

        if (cityByCode.isPresent()) {
            return cityMapper.apply(cityByCode.get());
        }

        City city = new City();
        city.setCode(cityRequest.getCode());
        city.setName(cityRequest.getName());
        city.setShortName(cityRequest.getShortName());

        final City save = redisCityRepository.save(city);

        redisCacheService.putCityToCache(cityRequest.getCode(), save);

        return cityMapper.apply(save);
    }

    public void deleteCityById(String cityId) {
        final Optional<City> byId = redisCityRepository.findById(cityId);

        final City city = byId.orElseThrow(() -> new CityNotFoundException("City not found by ID : " + cityId));

        redisCacheService.deleteCityFromCache(city.getCode());
        redisCityRepository.deleteById(cityId);
    }
}