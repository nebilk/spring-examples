package tr.com.nebildev.springredis.repository;

import org.springframework.data.repository.CrudRepository;
import tr.com.nebildev.springredis.entity.City;

import java.util.List;
import java.util.Optional;

public interface RedisCityRepository extends CrudRepository<City, String> {

    Optional<City> findCitiesByCode(String code);

    List<City> findAll();
}